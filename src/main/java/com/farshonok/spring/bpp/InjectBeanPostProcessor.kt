package com.farshonok.spring.bpp

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.util.ReflectionUtils


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY)
annotation class InjectBean

@Component
class InjectBeanPostProcessor : BeanPostProcessor, ApplicationContextAware {
    private var applicationContext: ApplicationContext? = null

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        bean.javaClass.getDeclaredFields()
            .filter({ field -> field.isAnnotationPresent(InjectBean::class.java) })
            .forEach({ field ->
                val beanToInject = applicationContext?.getBean(field.type)
                ReflectionUtils.makeAccessible(field)
                ReflectionUtils.setField(field, bean, beanToInject)
            })

        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        return bean
    }

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }
}
package com.farshonok.spring.bpp

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.Proxy


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE)
annotation class Auditing

@Component
class AuditingBeanPostProcessor : BeanPostProcessor {
    private val auditBeans: MutableMap<String?, Class<*>?> = HashMap<String?, Class<*>?>()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean.javaClass.isAnnotationPresent(Auditing::class.java)) {
            auditBeans.put(beanName, bean.javaClass)
        }
        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val beanClass = auditBeans.get(beanName)
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                beanClass.getClassLoader(), beanClass.getInterfaces(),
                { proxy, method, args ->
                    println("Audit method: " + method.getName())
                    val startTime = System.nanoTime()
                    try {
                        return@newProxyInstance method.invoke(bean, args)
                    } finally {
                        println("Time execution: " + (System.nanoTime() - startTime))
                    }
                })
        }
        return bean
    }
}
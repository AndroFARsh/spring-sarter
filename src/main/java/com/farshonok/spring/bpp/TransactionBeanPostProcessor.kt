package com.farshonok.spring.bpp

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.lang.reflect.Proxy


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE)
annotation class Transaction

@Component
class TransactionBeanPostProcessor : BeanPostProcessor {
    private val transactionBeans: MutableMap<String?, Class<*>?> = HashMap<String?, Class<*>?>()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean.javaClass.isAnnotationPresent(Transaction::class.java)) {
            transactionBeans[beanName] = bean.javaClass
        }
        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        val beanClass = transactionBeans.get(beanName)
        if (beanClass != null) {
            return Proxy.newProxyInstance(
                beanClass.getClassLoader(), beanClass.getInterfaces(),
                { _, method, args ->
                    println("Open transaction")
                    try {
                        return@newProxyInstance method.invoke(bean, args)
                    } catch (exception: Exception) {
                        println("Rollback transaction")
                        throw exception
                    } finally {
                        println("Close transaction")
                    }
                })
        }
        return bean
    }
}
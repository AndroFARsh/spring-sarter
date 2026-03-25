package com.farshonok.spring.bfpp

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component

@Component
class VerifyPropertyBeanFactoryPostProcessor : BeanFactoryPostProcessor, Ordered {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        println("VerifyPropertyBeanFactoryPostProcessor")
    }

    override fun getOrder(): Int = 0
}
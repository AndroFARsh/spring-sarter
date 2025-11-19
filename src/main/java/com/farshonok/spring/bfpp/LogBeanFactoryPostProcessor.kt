package com.farshonok.spring.bfpp

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.core.PriorityOrdered

class LogBeanFactoryPostProcessor : BeanFactoryPostProcessor, PriorityOrdered {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        println("LogBeanFactoryPostProcessor::postProcessBeanFactory $beanFactory")
    }

    override fun getOrder(): Int = PriorityOrdered.HIGHEST_PRECEDENCE
}


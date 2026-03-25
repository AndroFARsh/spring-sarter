package com.farshonok.spring.bfpp

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.core.Ordered
import org.springframework.core.PriorityOrdered
import org.springframework.stereotype.Component


@Component
class LogBeanFactoryPostProcessor : BeanFactoryPostProcessor, PriorityOrdered {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        for (beanDefinitionName in beanFactory.beanDefinitionNames) {
            val beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName)
            val genericArgumentValues = beanDefinition.constructorArgumentValues.genericArgumentValues
            for (genericArgumentValue in genericArgumentValues) {
                // TODO: 20.11.2021 set get
            }
        }
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}


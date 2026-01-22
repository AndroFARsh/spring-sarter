package com.farshonok.spring.database.pool

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class ConnectionPool : InitializingBean, DisposableBean {

    init {
        println("constructor")
    }

    @PostConstruct
    fun preInitialize() {
        println("preInitialize::Initializing...")
    }

    override fun afterPropertiesSet() {
        println("afterPropertiesSet::Initializing...")
    }

    @PreDestroy
    fun preDestroy() {
        println("preDestroy::Destroying...")
    }

    override fun destroy(){
        println("destroy::Destroying...")
    }
}
package com.farshonok.spring.database.pool

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import java.util.Objects

class ConnectionPool(
    val userName: String,
    val poolSize: Int,
    val args: List<Object>,
    properties: Map<String, Object>,
) : InitializingBean, DisposableBean {

    var properties: Map<String, Object> = properties
        get
        set(value) {
            println("properties::set $value")
            field = value
        }

    fun initialize() {
        println("initialize::Initializing...")
    }

    override fun afterPropertiesSet() {
        println("afterPropertiesSet::Initializing...")
    }

    fun destroyMethod(){
        println("destroyMethod::Destroying...")
    }

    override fun destroy(){
        println("destroy::Destroying...")
    }
}
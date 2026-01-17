package com.farshonok.web

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
//@ConfigurationProperties("db")
data class WebConfig(
    val username: String = "test",
    val password: String = "password",
)

package com.farshonok.spring.configs

import com.farshonok.spring.database.pool.ConnectionPool
import com.farshonok.web.WebConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@Import(WebConfig::class)
@Configuration
//@EnableConfigurationProperties(DatabaseProperties::class)
open class ApplicationConfig {

//    @Bean
//    open fun webPool(webConfig: WebConfig) =
//        ConnectionPool(webConfig.username, 1)
}
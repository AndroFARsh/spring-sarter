package com.farshonok.common.logger.starter.config

import com.farshonok.common.logger.starter.aop.CommonAspect
import com.farshonok.common.logger.starter.aop.FirstAspect
import com.farshonok.common.logger.starter.aop.SecondAspect
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Configuration
@EnableConfigurationProperties(LoggingProperties::class)
@ConditionalOnClass(LoggingProperties::class)
@ConditionalOnProperty(prefix = "app.common.logging", name = ["enabled"], havingValue = "true")
class LoggingAutoConfiguration {

    private val log = LoggerFactory.getLogger(LoggingProperties::class.java)

    @PostConstruct
    fun initialized() {
        log.info("Initializing autoconfig: {}", this)
    }

    @Bean
    fun commonAspect() = CommonAspect()

    @Bean
    @Order(1)
    fun firstAspect() = FirstAspect()

    @Bean
    @Order(2)
    fun secondAspect() = SecondAspect()
}
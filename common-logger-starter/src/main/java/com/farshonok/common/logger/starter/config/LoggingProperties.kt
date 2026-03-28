package com.farshonok.common.logger.starter.config

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.common.logging")
class LoggingProperties(
    val enabled: Boolean = true,
    val logLevel: Level = Level.INFO,
) {
    private val log = LoggerFactory.getLogger(LoggingProperties::class.java)

    @PostConstruct
    fun initialized() {
        log.info("Initializing properties: {}", this)
    }
}

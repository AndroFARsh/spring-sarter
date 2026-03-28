package com.farshonok.simple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SimpleProperties.class)
@ConditionalOnClass(SimpleProperties.class)
@ConditionalOnProperty(prefix = "app.simple", name = "enabled", havingValue = "true")
public class SimpleAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(com.farshonok.simple.config.SimpleProperties.class);

    @jakarta.annotation.PostConstruct
    public void init() {
        log.info("Initialized: {}", this);
    }

}

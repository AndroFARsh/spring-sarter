package com.farshonok.simple.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.simple")
public class SimpleProperties {
    /**
     * enable simple starter
     */
    private boolean enabled;

    private final Logger log = LoggerFactory.getLogger(com.farshonok.simple.config.SimpleProperties.class);

    @jakarta.annotation.PostConstruct
    public void init() {
        log.info("Initialized: {}", this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

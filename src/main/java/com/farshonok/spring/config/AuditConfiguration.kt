package com.farshonok.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@EnableJpaAuditing
@Configuration
class AuditConfiguration {

    @Bean
    fun provideAuditorAware(): AuditorAware<String> {
        // SecurityContext.getUser
        return { Optional.of("farshonok") }
    }
}
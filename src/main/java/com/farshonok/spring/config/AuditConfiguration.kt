package com.farshonok.spring.config

import com.farshonok.spring.DemoApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.envers.repository.config.EnableEnversRepositories
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = [ DemoApplication::class ])
@Configuration
class AuditConfiguration {

    @Bean
    fun provideAuditorAware(): AuditorAware<String> {
        // SecurityContext.getUser
        return { Optional.of("farshonok") }
    }
}
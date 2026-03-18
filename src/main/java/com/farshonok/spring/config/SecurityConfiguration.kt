package com.farshonok.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests { authorize ->
            authorize
                // Allow access to public paths and custom login page
                .requestMatchers("/public/**", "/login")
                .permitAll()

                // Other requests require authentification
                .anyRequest()
                .authenticated()
        }
        .httpBasic(Customizer.withDefaults())
//        .formLogin { formLogin ->
//            formLogin // Use the FormLoginConfigurer
//                .loginPage("/login") // Specify the custom login page URL
//                .defaultSuccessUrl("/users", true) // Redirect to /home after successful login
//                .failureUrl("/login?error=true") // Redirect to /login?error=true on failure
//                .permitAll()
//        }
        .build()
}
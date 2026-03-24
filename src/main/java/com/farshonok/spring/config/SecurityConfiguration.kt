package com.farshonok.spring.config

import com.farshonok.spring.database.entities.Role.ADMIN
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        oidcUserService: OAuth2UserService<OidcUserRequest, OidcUser>,
    ): SecurityFilterChain = http
//        .csrf { it.disable() }
        .authorizeHttpRequests { config ->
            config
                // Allow access to public paths and custom login page
                .requestMatchers("/public/**", "/login", "/users/signup", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()

                // Allow access to admin resourses
                .requestMatchers(HttpMethod.POST,"/users/{id}/delete").hasAuthority(ADMIN.authority)
                .requestMatchers("/admin/**").hasAuthority(ADMIN.authority)

                // Other requests require authentification
                .anyRequest().authenticated()

        }
//        .httpBasic(Customizer.withDefaults())
        .formLogin { config ->
            config // Use the FormLoginConfigurer
                .loginPage("/login") // Specify the custom login page URL
                .defaultSuccessUrl("/users", true) // Redirect to /home after successful login
                .failureUrl("/login?error=true") // Redirect to /login?error=true on failure
                .permitAll()
        }
        .oauth2Login { config ->
            config
                .loginPage("/login")
                .defaultSuccessUrl("/users", true) // Redirect to /home after successful login
                .userInfoEndpoint { endpoint -> endpoint.oidcUserService(oidcUserService) }
        }
        .logout { config ->
            config
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
        }
        .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        return passwordEncoder
    }


}
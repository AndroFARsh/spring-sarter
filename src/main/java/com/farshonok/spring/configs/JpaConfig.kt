package com.farshonok.spring.configs

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.core.type.AnnotatedTypeMetadata
import kotlin.jvm.Throws

@Conditional(JpaCondition::class)
@Configuration
open class JpaConfig {

    @PostConstruct
    fun postInit() {
        println("Jpa configuration is enabled")
    }
}

class JpaCondition : Condition {

    override fun matches(
        context: ConditionContext,
        metadata: AnnotatedTypeMetadata
    ): Boolean {
        try {
            val classLoader = context.classLoader ?: return false
            classLoader.loadClass("org.postgresql.Driver")
            return true
        } catch (_: Throwable) {
            return false
        }
    }
}
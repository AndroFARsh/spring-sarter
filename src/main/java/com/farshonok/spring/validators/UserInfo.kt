package com.farshonok.spring.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [UserInfoValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserInfo (
    val message: String = "User first name or user last name must be specified",

    val groups: Array<KClass<*>> = [],

    val payload: Array<KClass<out Payload>> = [],
)


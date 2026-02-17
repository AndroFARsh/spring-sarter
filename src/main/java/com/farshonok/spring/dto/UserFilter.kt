package com.farshonok.spring.dto

import java.time.LocalDate

data class UserFilter(
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: LocalDate? = null,
)
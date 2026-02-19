package com.farshonok.spring.dto

import com.farshonok.spring.database.entities.Role
import java.time.LocalDate

class UserReadDto(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate?,
    val role: Role,
    val company: CompanyReadDto?,
    val image: String?,
)

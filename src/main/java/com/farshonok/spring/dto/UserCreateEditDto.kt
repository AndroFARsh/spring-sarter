package com.farshonok.spring.dto

import com.farshonok.spring.database.entities.Role
import java.time.LocalDate

class UserCreateEditDto(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val birthDate: LocalDate?,
    val role: Role?,
    val companyId: Int = 0,
)


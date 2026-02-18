package com.farshonok.spring.dto

import com.farshonok.spring.database.entities.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.time.LocalDate

class UserCreateEditDto(
    // validation should be explicitly defined on the getter, otherwise it will not work for some reason
    @get:Email
    @get:NotBlank
    val email: String?,

    @get:NotBlank
    @get:Size(min = 3, max = 64)
    val firstName: String?,

    @get:NotBlank
    @get:Size(min = 3, max = 64)
    val lastName: String?,

//    @PastOrPresent
    val birthDate: LocalDate?,

    @get:NotNull
    val role: Role?,

    @get:PositiveOrZero
    val companyId: Int = 0,
)


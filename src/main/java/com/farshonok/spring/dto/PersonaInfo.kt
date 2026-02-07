package com.farshonok.spring.dto

import java.time.LocalDate

data class PersonaInfo(
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
)

interface IPersonaInfo {
    val firstName: String
    val lastName: String
    val birthDate: LocalDate
}
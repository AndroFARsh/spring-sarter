package com.farshonok.spring.mappers

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.entities.User
import com.farshonok.spring.dto.CompanyReadDto
import com.farshonok.spring.dto.UserReadDto
import org.springframework.stereotype.Component

@Component
class UserReadMapper(
    val companyReadMapper: Mapper<Company, CompanyReadDto>
) : Mapper<User, UserReadDto> {

    override fun map(from: User): UserReadDto {
        return UserReadDto(
            id = from.id,
            email = from.email,
            firstName = from.firstName,
            lastName = from.lastName,
            birthDate = from.birthDate,
            role = from.role,
            company = from.company?.let(companyReadMapper::map)
        )
    }
}


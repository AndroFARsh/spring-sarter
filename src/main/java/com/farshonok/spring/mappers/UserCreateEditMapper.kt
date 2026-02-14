package com.farshonok.spring.mappers

import com.farshonok.spring.database.entities.User
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.dto.UserCreateEditDto
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class UserCreateEditMapper(
    private val companyRepository: CompanyRepository,
) : Mapper<UserCreateEditDto, User> {

    override fun map(from: UserCreateEditDto, to: User) : User {
        return to.apply {
            email = from.email
            firstName = from.firstName
            lastName = from.lastName
            birthDate = from.birthDate
            role = from.role
            company = companyRepository.findById(from.companyId).getOrNull()
        }
    }

    override fun map(from: UserCreateEditDto) = User(
        email = from.email,
        firstName = from.firstName,
        lastName = from.lastName,
        birthDate = from.birthDate,
        role = from.role,
    ).apply {
        company = companyRepository.findById(from.companyId).getOrNull()
    }
}
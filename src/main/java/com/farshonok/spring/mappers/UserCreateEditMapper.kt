package com.farshonok.spring.mappers

import com.farshonok.spring.database.entities.User
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.service.ImageService
import org.springframework.stereotype.Component
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

@Component
class UserCreateEditMapper(
    private val companyRepository: CompanyRepository,
    private val imageService: ImageService,
) : Mapper<UserCreateEditDto, User> {

    override fun map(from: UserCreateEditDto, to: User) : User {
        return to.apply {
            email = from.email ?: email
            firstName = from.firstName ?: firstName
            lastName = from.lastName ?: lastName
            birthDate = from.birthDate ?: birthDate
            role = from.role ?: role
            company = companyRepository.findById(from.companyId).getOrNull()

            from.image
                .filter { !it.isEmpty }
                .ifPresent { image = it.originalFilename ?: return@ifPresent }
        }
    }

    override fun map(from: UserCreateEditDto) = User(
        email = from.email!!,
        firstName = from.firstName!!,
        lastName = from.lastName!!,
        birthDate = from.birthDate,
        role = from.role!!,
    ).apply {
        company = companyRepository.findById(from.companyId).getOrNull()

        from.image
            .filter { !it.isEmpty }
            .ifPresent { image = it.originalFilename ?: return@ifPresent }
    }
}
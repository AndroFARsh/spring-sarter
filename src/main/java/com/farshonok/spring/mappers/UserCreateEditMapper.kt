package com.farshonok.spring.mappers

import com.farshonok.spring.database.entities.User
import com.farshonok.spring.dto.UserCreateEditDto
import org.springframework.stereotype.Component

@Component
class UserCreateEditMapper : Mapper<UserCreateEditDto, User> {

    override fun map(from: UserCreateEditDto, entity: User) = entity.copy(
        email = from.email,
        firstName = from.firstName,
        lastName = from.lastName,
        birthDate = from.birthDate,
        role = from.role,
    )

    override fun map(from: UserCreateEditDto) = User(
        email = from.email,
        firstName = from.firstName,
        lastName = from.lastName,
        birthDate = from.birthDate,
        role = from.role,
    )


}
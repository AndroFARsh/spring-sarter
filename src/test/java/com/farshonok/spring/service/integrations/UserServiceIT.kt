package com.farshonok.spring.service.integrations

import com.farshonok.spring.BaseIntegrationTest
import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.database.pool.ConnectionPool
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.service.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import kotlin.String

class UserServiceIT(
    val userService: UserService
) : BaseIntegrationTest() {
    @Test
    fun find_all() {
        val result = userService.findAll()
        assertThat(result.size).isEqualTo(5)
    }

    @Test
    fun find_by_id() {
        val maybeUser = userService.findById(1)
        assertTrue(maybeUser.isPresent)
        assertThat(maybeUser.get().id).isEqualTo(1)
    }

    @Test
    fun create() {
        val create = UserCreateEditDto(
            email = "test@test.com",
            firstName = "Test Name",
            lastName = "Test Lastname",
            birthDate = LocalDate.ofYearDay(1986, 1),
            role = Role.ADMIN,
        )
        val result = assertDoesNotThrow { userService.create(create) }
        assertThat(result.email).isEqualTo(create.email)
        assertThat(result.firstName).isEqualTo(create.firstName)
        assertThat(result.lastName).isEqualTo(create.lastName)
        assertThat(result.birthDate).isEqualTo(create.birthDate)
        assertThat(result.role).isSameAs(create.role)
    }

    @Test
    fun update() {
        val update = UserCreateEditDto(
            email = "test@test.com",
            firstName = "Test Name",
            lastName = "Test Lastname",
            birthDate = LocalDate.ofYearDay(1986, 1),
            role = Role.ADMIN,
        )
        val maybeResult = userService.update(1, update)
        assertTrue(maybeResult.isPresent)
        maybeResult.ifPresent { result ->
            assertThat(result.email).isEqualTo(update.email)
            assertThat(result.firstName).isEqualTo(update.firstName)
            assertThat(result.lastName).isEqualTo(update.lastName)
            assertThat(result.birthDate).isEqualTo(update.birthDate)
            assertThat(result.role).isSameAs(update.role)
        }
    }

    @Test
    fun delete() {
        assertTrue { userService.delete(1) }
        assertFalse { userService.delete(1) }
    }
}
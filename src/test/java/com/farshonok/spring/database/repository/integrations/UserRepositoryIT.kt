package com.farshonok.spring.database.repository.integrations

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.database.entities.Role.ADMIN
import com.farshonok.spring.database.entities.Role.USER
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.service.annotations.IT
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDate

@IT
@Transactional
class UserRepositoryIT(
    val userRepository: UserRepository
) {
    @Test
    fun findAllBy() {
        val users = userRepository.findAllBy("a", "ov")
        assertThat(users).hasSize(3)
    }

    @Test
    fun findAllByUsername() {
        // native query not allowed operation contains
        val users = userRepository.findAllByUsername("petr@gmail.com")
        assertThat(users).hasSize(1)
    }

    @Test
    fun updateRole() {
        val date = LocalDate.now()
        val originUser2 = userRepository.getReferenceById(2)
        originUser2.birthDate = date
        assertEquals(originUser2.role, USER)

        val result = userRepository.updateRole(ADMIN, 2, 3, 4)
        assertTrue(result == 3)

        val updatedUser2 = userRepository.getReferenceById(2)
        assertEquals(updatedUser2.role, ADMIN)
        assertEquals(updatedUser2.birthDate, date)
    }

}

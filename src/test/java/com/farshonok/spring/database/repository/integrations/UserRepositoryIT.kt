package com.farshonok.spring.database.repository.integrations

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.service.annotations.IT
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

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
}

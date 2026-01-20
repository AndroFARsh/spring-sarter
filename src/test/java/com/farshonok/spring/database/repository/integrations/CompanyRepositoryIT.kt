package com.farshonok.spring.database.repository.integrations

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.service.annotations.IT
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional

@IT
@Transactional
//@Commit
class CompanyRepositoryIT(
    val entityManager: EntityManager,
) {
    @Test
    fun findById() {
        val company:Company = entityManager.find(Company::class.java, 1L)
        assertNotNull(company)
        assertThat(company.description).hasSize(2)
    }


    @Test
    fun save() {
        val company = Company(name = "Apple").apply {
            description = mapOf(
                "en" to "Apple description",
                "ua" to "Apple опис"
            )
        }

        entityManager.persist(company)
        assertThat(company.id).isGreaterThan(0)
    }
}
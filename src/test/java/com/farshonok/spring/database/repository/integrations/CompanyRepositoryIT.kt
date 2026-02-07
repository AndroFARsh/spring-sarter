package com.farshonok.spring.database.repository.integrations

import com.farshonok.spring.BaseIntegrationTest
import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.repository.CompanyRepository
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate

class CompanyRepositoryIT(
    val entityManager: EntityManager,
    val transactionTemplate: TransactionTemplate,
    val companyRepository: CompanyRepository
) : BaseIntegrationTest() {
    @Test
    fun findById() {
        transactionTemplate.execute {
            val company = entityManager.find(Company::class.java, 1L)
            assertNotNull(company)
            assertThat(company.description).hasSize(2)
        }
    }

    @Transactional
    //@Commit
    @Test
    fun delete() {
       val maybeCompany =  companyRepository.findById(1)
        assertTrue(maybeCompany.isPresent)
        maybeCompany.ifPresent(companyRepository::delete)
        entityManager.flush()
        assertTrue(companyRepository.findById(1).isEmpty)
    }

    @Transactional
    //@Commit
    @Test
    fun deleteById() {
        companyRepository.deleteById(1)
        entityManager.flush()
        assertTrue(companyRepository.findById(1).isEmpty)
    }

    @Transactional
    //@Commit
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

    @Transactional
    //@Commit
    @Test
    fun findByQueries() {
        companyRepository.findByNameIgnoreCase("GOOGLE").also {
            assertTrue(it.isPresent)
            assertThat(it.get().description).hasSize(2)
        }


        val maybeCompany =  companyRepository.findByName("google")
        assertTrue(maybeCompany.isPresent)
        val companies = companyRepository.findAllByNameContainsIgnoreCase("a")
        assertTrue(companies.size == 2)
    }
}
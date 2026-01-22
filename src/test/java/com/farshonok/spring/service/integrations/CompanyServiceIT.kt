package com.farshonok.spring.service.integrations

import com.farshonok.spring.service.CompanyService
import com.farshonok.spring.service.annotations.IT
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional

@IT
@Transactional
class CompanyServiceIT(
    val entityManager: EntityManager,
    val companyService: CompanyService
) {
    @Test
    fun findById() {
        val dto = companyService.findById(1)
        assertTrue(dto.isPresent)
        assertEquals(COMPANY_ID, dto.get().id)
    }

    @Test
    fun delete() {
        assertTrue { companyService.delete(1) }
        entityManager.flush()
        assertTrue { !companyService.delete(1) }
    }

    companion object {
        private const val COMPANY_ID = 1
    }
}
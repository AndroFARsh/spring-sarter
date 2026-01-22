package com.farshonok.spring.service

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.events.EntityEvent
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher
import java.util.Optional

class CompanyServiceMockkTest {

    private val companyRepository: CompanyRepository = mockk(relaxed = true)
    private val userService: UserService = mockk(relaxed = true)
    private val eventPublisher: ApplicationEventPublisher = mockk(relaxed = true)

    lateinit var companyService: CompanyService

    @BeforeEach
    fun setUp() {
        companyService = CompanyService(companyRepository, userService, eventPublisher)

        every { companyRepository.findById(COMPANY_ID) } returns Optional.of(Company("").apply { id=COMPANY_ID })
    }

    @Test
    fun findById() {
        // Add your test logic here
        val dto = companyService.findById(1)
        assertTrue(dto.isPresent)
        assertEquals(COMPANY_ID, dto.get().id)

        verify(exactly = 1) { eventPublisher.publishEvent(any<EntityEvent>()) }
    }

    companion object {
        private const val COMPANY_ID = 1
    }

}
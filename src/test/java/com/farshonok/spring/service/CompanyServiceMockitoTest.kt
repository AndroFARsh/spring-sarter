package com.farshonok.spring.service

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.dto.CompanyReadDto
import com.farshonok.spring.events.EntityEvent
import com.farshonok.spring.mappers.CompanyReadMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@ExtendWith(MockitoExtension::class)
class CompanyServiceMockitoTest {

    @Mock
    lateinit var companyReadMapper: CompanyReadMapper
    @Mock
    lateinit var companyRepository: CompanyRepository
    @Mock
    lateinit var userService: UserService
    @Mock
    lateinit var eventPublisher: ApplicationEventPublisher
    @InjectMocks
    lateinit var companyService: CompanyService

    @Test
    fun findById() {
        val company = Company(id = COMPANY_ID, name = "Test company")
        doReturn(Optional.of(company))
            .`when`(companyRepository).findById(COMPANY_ID)

        doReturn(CompanyReadDto( id = COMPANY_ID,"Test company"))
            .`when`(companyReadMapper).map(company)

        val dto = companyService.findById(COMPANY_ID)
        assertTrue(dto.isPresent)
        assertEquals(COMPANY_ID, dto.get().id)

        verify(eventPublisher).publishEvent(any<EntityEvent>())
        verifyNoMoreInteractions(eventPublisher, userService)
    }

    companion object {
        private const val COMPANY_ID = 1
    }

}
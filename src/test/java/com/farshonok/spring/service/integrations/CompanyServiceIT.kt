package com.farshonok.spring.service.integrations

import com.farshonok.spring.DemoApplication
import com.farshonok.spring.events.EntityEvent
import com.farshonok.spring.service.CompanyService
import com.farshonok.spring.service.CompanyServiceMockitoTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension


//@ExtendWith(SpringExtension::class)
//@ContextConfiguration(classes = [DemoApplication::class], initializers = [ConfigDataApplicationContextInitializer::class])
@SpringBootTest
class CompanyServiceMockkTest {

//    private val companyRepository: CrudRepository<Int, Company> = mockk(relaxed = true)
//    private val userService: UserService = mockk(relaxed = true)
//    private val eventPublisher: ApplicationEventPublisher = mockk(relaxed = true)
//
    @Autowired
    lateinit var companyService: CompanyService

    @Test
    fun findById() {
        val dto = companyService.findById(1)
        assertTrue(dto.isPresent)
        assertEquals(COMPANY_ID, dto.get().id)
    }

    companion object {
        private const val COMPANY_ID = 1
    }

}
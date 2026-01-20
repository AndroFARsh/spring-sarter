package com.farshonok.spring.service.integrations

import com.farshonok.spring.service.CompanyService
import com.farshonok.spring.service.annotations.IT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


//@ExtendWith(SpringExtension::class)
//@ContextConfiguration(classes = [DemoApplication::class], initializers = [ConfigDataApplicationContextInitializer::class])
//@ActiveProfiles("test")
//@SpringBootTest
@IT
class CompanyServiceIT(
    val companyService: CompanyService
) {

//    @Autowired
//    lateinit var companyService: CompanyService

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
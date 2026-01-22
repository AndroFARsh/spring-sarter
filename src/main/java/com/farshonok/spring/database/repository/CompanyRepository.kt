package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.Company
import java.util.Optional

interface CompanyRepository : org.springframework.data.repository.Repository<Company, Int> {
    fun findById(id: Int, properties: Map<String, String> = emptyMap()): Optional<Company>

    fun delete(company: Company)
}
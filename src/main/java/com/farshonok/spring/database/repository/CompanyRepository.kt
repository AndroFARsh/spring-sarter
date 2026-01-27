package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.Company
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CompanyRepository : JpaRepository<Company, Int> {
    fun findById(id: Int, properties: Map<String, String> = emptyMap()): Optional<Company>

//    fun delete(company: Company)
//    fun deleteById(id: Int)

    // Can return Optional, Company?, Future
    fun findByName(name: String): Optional<Company>

    // can return List, Stream
    fun findAllByNameContainsIgnoreCase(fragment: String): List<Company>
}
package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.Company
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class CompanyRepository() : CrudRepository<Int, Company>{
    override fun findById(id: Int): Optional<Company> {
        println("findById company by id: $id")
        return Optional.of(Company( "").apply { this.id = id })
    }

    override fun delete(id: Int): Boolean {
        println("deleteById company by id: $id")
        return false
    }

}
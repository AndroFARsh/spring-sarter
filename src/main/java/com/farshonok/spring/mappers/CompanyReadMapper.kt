package com.farshonok.spring.mappers

import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.dto.CompanyReadDto
import org.springframework.stereotype.Component

@Component
class CompanyReadMapper() : Mapper<Company, CompanyReadDto> {
    override fun map(from: Company): CompanyReadDto {
        return CompanyReadDto(id = from.id, name = from.name)
    }

}
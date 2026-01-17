package com.farshonok.spring.service

import com.farshonok.spring.database.entities.AccessType
import com.farshonok.spring.database.entities.Company
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.database.repository.CrudRepository
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.dto.CompanyReadDto
import com.farshonok.spring.events.EntityEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CompanyService(
    private val companyRepository: CrudRepository<Int, Company>,
    private val userService: UserService,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun findById(id: Int): Optional<CompanyReadDto> {
        return companyRepository.findById(id)
            .map { entity ->
                eventPublisher.publishEvent(EntityEvent(AccessType.READ, entity))
                CompanyReadDto(entity.id)
            }
    }

    fun delete(id: Int): Optional<Boolean> {
        return companyRepository.findById(id).map { entity ->
            eventPublisher.publishEvent(EntityEvent(AccessType.DELETE, entity))
            companyRepository.delete(id)
        }
    }
}


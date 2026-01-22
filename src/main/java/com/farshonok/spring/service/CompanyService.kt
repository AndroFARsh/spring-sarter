package com.farshonok.spring.service

import com.farshonok.spring.database.entities.AccessType
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.dto.CompanyReadDto
import com.farshonok.spring.events.EntityEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val userService: UserService,
    private val eventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun findById(id: Int): Optional<CompanyReadDto> {
        return companyRepository.findById(id)
            .map { entity ->
                eventPublisher.publishEvent(EntityEvent(AccessType.READ, entity))
                CompanyReadDto(id)
            }
    }

    @Transactional
    fun delete(id: Int): Boolean {
        val entity = companyRepository.findById(id)
        entity.ifPresent {
            eventPublisher.publishEvent(EntityEvent(AccessType.DELETE, it))
            companyRepository.delete(it)
        }
        return entity.isPresent
    }
}


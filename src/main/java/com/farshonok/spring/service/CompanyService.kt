package com.farshonok.spring.service

import com.farshonok.spring.database.entities.AccessType
import com.farshonok.spring.database.repository.CompanyRepository
import com.farshonok.spring.dto.CompanyReadDto
import com.farshonok.spring.events.EntityEvent
import com.farshonok.spring.mappers.CompanyReadMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import kotlin.collections.map

@Service
@Transactional(readOnly = true)
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val companyReadMapper: CompanyReadMapper,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun findAll() : List<CompanyReadDto> = companyRepository.findAll()
        .map(companyReadMapper::map)

    fun findById(id: Int): Optional<CompanyReadDto> = companyRepository.findById(id)
        .map { entity ->
            eventPublisher.publishEvent(EntityEvent(AccessType.READ, entity))
            companyReadMapper.map(entity)
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


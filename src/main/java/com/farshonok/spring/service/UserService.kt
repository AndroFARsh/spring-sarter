package com.farshonok.spring.service

import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.dto.UserFilter
import com.farshonok.spring.dto.UserReadDto
import com.farshonok.spring.mappers.UserCreateEditMapper
import com.farshonok.spring.mappers.UserReadMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import kotlin.collections.map

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userReadMapper: UserReadMapper,
    private val userCreateMapper: UserCreateEditMapper,
) {
    fun findAll(filter: UserFilter): List<UserReadDto> =
        userRepository.findAllByFilterQueryDSL(filter)
            .map(userReadMapper::map)

    fun findAll(): List<UserReadDto> =
        userRepository.findAll()
            .map(userReadMapper::map)

    fun findById(id: Int): Optional<UserReadDto> =
        userRepository.findById(id)
            .map(userReadMapper::map)

    @Transactional
    fun create(user: UserCreateEditDto): UserReadDto =
        Optional.of(user)
            .map(userCreateMapper::map)
            .map(userRepository::save)
            .map(userReadMapper::map)
            .orElseThrow()

    @Transactional
    fun update(id: Int, user: UserCreateEditDto): Optional<UserReadDto> =
        userRepository.findById(id)
            .map { entity -> userCreateMapper.map(user, entity) }
            .map {

                userRepository.saveAndFlush(it)

            }
            .map(userReadMapper::map)

    @Transactional
    fun delete(id: Int): Boolean =
        userRepository.findById(id)
            .map { entity ->
                userRepository.delete(entity)
                userRepository.flush()
                true
            }.orElse(false)

}


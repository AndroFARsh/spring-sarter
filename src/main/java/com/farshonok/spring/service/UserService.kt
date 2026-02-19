package com.farshonok.spring.service

import com.farshonok.spring.database.entities.QUser.Companion.user
import com.farshonok.spring.database.querydsl.QPredicates
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.dto.UserFilter
import com.farshonok.spring.dto.UserReadDto
import com.farshonok.spring.mappers.UserCreateEditMapper
import com.farshonok.spring.mappers.UserReadMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.Optional
import kotlin.collections.map

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userReadMapper: UserReadMapper,
    private val userCreateMapper: UserCreateEditMapper,
    private val imageService: ImageService,
) {
    fun findAll(filter: UserFilter, pageable: Pageable): Page<UserReadDto> =
        userRepository.findAll(
            QPredicates.builder()
                .add(filter.firstName, user.firstName::containsIgnoreCase)
                .add(filter.lastName, user.lastName::containsIgnoreCase)
                .add(filter.birthDate, user.birthDate::eq)
                .build(),
            pageable
        ).map(userReadMapper::map)

    fun findAll(): List<UserReadDto> =
        userRepository.findAll()
            .map(userReadMapper::map)

    fun findById(id: Int): Optional<UserReadDto> =
        userRepository.findById(id)
            .map(userReadMapper::map)

    @Transactional
    fun create(user: UserCreateEditDto): UserReadDto =
        Optional.of(user)
            .map{ dto ->
                dto.image.ifPresent { uploadImage(it) }
                userCreateMapper.map(dto)
            }
            .map(userRepository::save)
            .map(userReadMapper::map)
            .orElseThrow()

    @Transactional
    fun update(id: Int, user: UserCreateEditDto): Optional<UserReadDto> =
        userRepository.findById(id)
            .map { entity ->
                user.image.ifPresent { uploadImage(it) }
                userCreateMapper.map(user, entity)
            }
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

    fun uploadImage(image: MultipartFile) {
        if (!image.isEmpty) {
            imageService.upload(image.originalFilename ?: return, image.inputStream)
        }
    }
}


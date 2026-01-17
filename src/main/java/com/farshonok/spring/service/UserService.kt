package com.farshonok.spring.service

import com.farshonok.spring.database.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
)


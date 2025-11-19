package com.farshonok.spring.service

import com.farshonok.spring.database.repository.UserRepository

class UserService(
    private val userRepository: UserRepository
)


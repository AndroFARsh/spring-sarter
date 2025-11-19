package com.farshonok.spring

import com.farshonok.spring.database.pool.ConnectionPool
import com.farshonok.spring.database.repository.UserRepository
import com.farshonok.spring.service.UserService

fun main(args: Array<String>) {
    print("HELO WORLD")
    val connectionPool = ConnectionPool()
    val userRepository = UserRepository(connectionPool)
    val userService = UserService(userRepository)

}
package com.farshonok.spring.service.integrations

import com.farshonok.spring.database.pool.ConnectionPool
import com.farshonok.spring.service.UserService
import com.farshonok.spring.service.annotations.IT
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IT
class UserServiceIT {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var  connectionPool: ConnectionPool

    @Test
    fun test() {
    }
}
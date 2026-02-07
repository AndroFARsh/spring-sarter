package com.farshonok.spring.service.integrations

import com.farshonok.spring.BaseIntegrationTest
import com.farshonok.spring.database.pool.ConnectionPool
import com.farshonok.spring.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UserServiceIT : BaseIntegrationTest() {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var  connectionPool: ConnectionPool

    @Test
    fun test() {
    }
}
package com.farshonok.spring.service.integrations

import com.farshonok.spring.database.pool.ConnectionPool
import com.farshonok.spring.service.UserService
import com.farshonok.spring.service.annotations.IntegrationsTests
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean

@IntegrationsTests
class UserServiceIT {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var  connectionPool: ConnectionPool

    @Test
    fun test() {
    }
}
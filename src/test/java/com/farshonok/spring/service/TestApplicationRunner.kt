package com.farshonok.spring.service

import com.farshonok.spring.database.pool.ConnectionPool
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.test.context.bean.override.mockito.MockitoBean

@TestConfiguration
class TestApplicationRunner {

    @MockitoBean
    lateinit var  connectionPool: ConnectionPool
}
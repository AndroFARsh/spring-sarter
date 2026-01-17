package com.farshonok.spring.database.repository

import com.farshonok.spring.database.pool.ConnectionPool
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val connectionPool: ConnectionPool
)



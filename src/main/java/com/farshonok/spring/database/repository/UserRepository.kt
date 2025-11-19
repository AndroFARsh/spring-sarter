package com.farshonok.spring.database.repository

import com.farshonok.spring.database.pool.ConnectionPool

class UserRepository(
    private val connectionPool: ConnectionPool
) {

}


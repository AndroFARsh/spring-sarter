package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

// Annotation @Repository is optional if we extend org.springframework.data.repository.Repository
//@Repository
interface UserRepository : JpaRepository<User, Int> {
    @Query("""
        select u from User u
        where u.firstName like %:firstname% and u.lastName like %:lastname%
    """)
    fun findAllBy(firstname: String, lastname: String): List<User>

    // Native query example, not allowed operations: contains, startsWith, endsWith, etc.
    @Query(nativeQuery = true, value = """
        SELECT u.* FROM users u
        WHERE u.username = :username
    """)
    fun findAllByUsername(username: String): List<User>
}



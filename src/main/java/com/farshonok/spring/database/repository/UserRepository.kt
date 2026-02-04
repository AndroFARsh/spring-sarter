package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.database.entities.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

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

    // Modifying annotation is required for update and delete queries
    // clearAutomatically = true to avoid stale state in the persistence context
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update User u 
        set u.role = :role
        where u.id in (:ids)
    """)
    fun updateRole(role: Role, vararg ids: Int): Int

    fun findFirstByOrderByIdDesc(): Optional<User>

    fun findTopByOrderByIdDesc(): List<User>

    fun findTop3ByOrderByIdDesc(): List<User>

    fun findTopBy(sort: Sort): Optional<User>

    fun findBy(pageable: Pageable): List<User>
}



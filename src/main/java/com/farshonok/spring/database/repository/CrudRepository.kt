package com.farshonok.spring.database.repository

import java.util.Optional

interface CrudRepository<K, E> {
    fun findById(id: K): Optional<E>

    fun delete(id: K): Boolean
}
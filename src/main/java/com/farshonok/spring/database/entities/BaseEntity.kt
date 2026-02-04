package com.farshonok.spring.database.entities

import java.io.Serializable

interface BaseEntity<T: Serializable> {
    val id: T
}
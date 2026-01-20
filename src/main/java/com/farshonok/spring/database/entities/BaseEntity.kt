package com.farshonok.spring.database.entities

import java.io.Serializable

interface BaseEntity<T: Serializable> {
    var id: T
}
package com.farshonok.spring.events

import com.farshonok.spring.database.entities.AccessType
import com.farshonok.spring.database.entities.BaseEntity
import java.util.EventObject

data class EntityEvent(
    val accessType: AccessType,
    val entity: BaseEntity<*>,
) : EventObject(entity)
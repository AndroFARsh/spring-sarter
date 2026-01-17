package com.farshonok.spring.events

import com.farshonok.spring.database.entities.AccessType
import com.farshonok.spring.database.entities.Entity
import java.util.EventObject

data class EntityEvent(
    val accessType: AccessType,
    val entity: Entity,
) : EventObject(entity)
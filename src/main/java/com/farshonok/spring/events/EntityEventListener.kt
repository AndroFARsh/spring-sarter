package com.farshonok.spring.events

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component


@Component
class EntityEventListener {

    @EventListener(condition = "#a0.accessType.name() == 'READ'")
    fun acceptEntityRaad(event: EntityEvent) {
        println("Entity READ event accepted: $event")
    }

    @EventListener(condition = "#p0.accessType.name() == 'DELETE'")
    fun acceptEntityDelete(event: EntityEvent) {
        println("Entity DELETE event accepted: $event")
    }
}
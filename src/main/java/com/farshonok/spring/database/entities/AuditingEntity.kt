package com.farshonok.spring.database.entities

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditingEntity<T: Serializable> : BaseEntity<T> {
    @CreatedDate
    var createdAt: Instant? = null

    @LastModifiedDate
    var modifiedAt: Instant? = null

    @CreatedBy
    var createdBy: String? = null

    @LastModifiedBy
    var modifiedBy: String? = null
}
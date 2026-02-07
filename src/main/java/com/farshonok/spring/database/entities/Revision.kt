package com.farshonok.spring.database.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp

@Entity
@RevisionEntity
data class Revision(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    override val id: Int,

    @RevisionTimestamp
    val timestamp: Long,
) : BaseEntity<Int>
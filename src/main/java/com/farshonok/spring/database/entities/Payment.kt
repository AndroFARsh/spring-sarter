package com.farshonok.spring.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int = 0,

    @Column(name = "amount")
    var amount: Int
) : BaseEntity<Int> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    var receiver: User? = null
}
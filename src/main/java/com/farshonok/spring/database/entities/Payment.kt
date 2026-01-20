package com.farshonok.spring.database.entities

import jakarta.persistence.*

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int = 0,
    @Column(name = "amount")
    var amount: Int
) : BaseEntity<Int> {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    var receiver: User? = null
}
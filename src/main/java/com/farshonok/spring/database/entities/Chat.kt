package com.farshonok.spring.database.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "chat")
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int = 0,

    @Column(nullable = false)
    var name: String,
) : BaseEntity<Int> {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    var userChats: MutableList<UserChat> = mutableListOf()
}
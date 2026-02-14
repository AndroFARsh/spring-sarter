package com.farshonok.spring.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "chat")
class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Int = 0,

    @Column(nullable = false)
    var name: String,
) : BaseEntity<Int> {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    var userChats: MutableList<UserChat> = mutableListOf()
}
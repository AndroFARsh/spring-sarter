package com.farshonok.spring.database.entities

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

enum class Role {
    ADMIN,
    USER,
    GUEST
}

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int = 0,

    @Column(name = "username", nullable = false, unique = true)
    var name: String,
    @Column(name = "firstname")
    var firstName: String,
    @Column(name = "lastname")
    var lastName: String,

    @Column(name = "birth_date")
    var birthDate: LocalDate?,
    @Enumerated(EnumType.STRING)
    var role: Role,
) : BaseEntity<Int> {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var userChats: MutableList<UserChat> = mutableListOf()
}
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
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.NamedEntityGraph
import java.time.LocalDate

enum class Role {
    ADMIN,
    USER,
    GUEST
}

@NamedEntityGraph(name = "User.company", graph = "company")
@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Int = 0,

    @Column(name = "username", nullable = false, unique = true)
    override var name: String,
    @Column(name = "firstname")
    override var firstName: String,
    @Column(name = "lastname")
    override var lastName: String,

    @Column(name = "birth_date")
    override var birthDate: LocalDate?,
    @Enumerated(EnumType.STRING)
    override var role: Role,
) : BaseEntity<Int>, UserSearch {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var userChats: MutableList<UserChat> = mutableListOf()
}

interface UserSearch {
    val name: String
    val firstName: String
    val lastName: String
    val birthDate: LocalDate?
    val role: Role
}
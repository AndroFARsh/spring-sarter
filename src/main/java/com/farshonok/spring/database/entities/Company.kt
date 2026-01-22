package com.farshonok.spring.database.entities

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapKeyColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "company")
data class Company(
    @Column(nullable = false, unique = true)
    var name: String,
) : BaseEntity<Int> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Int = 0

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "company_locales", joinColumns = [JoinColumn(name = "company_id")])
    @MapKeyColumn(name = "lang")
    //@MapValueColumn(name = "description")
    var description: Map<String, String> = mutableMapOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    var users: MutableList<User> = mutableListOf()
}
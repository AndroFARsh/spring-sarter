package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.QUser.Companion.user
import com.farshonok.spring.database.entities.User
import com.farshonok.spring.database.querydsl.QPredicates
import com.farshonok.spring.dto.UserFilter
import com.querydsl.jpa.impl.JPAQuery
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.Predicate
import java.time.LocalDate

interface FilterUserRepository {
    fun findAllByFilter(filter: UserFilter): List<User>
    fun findAllByFilterQueryDSL(filter: UserFilter): List<User>
}

class FilterUserRepositoryImpl(
    val entityManager: EntityManager,
) : FilterUserRepository {

    override fun findAllByFilter(filter: UserFilter): List<User> {
        val cb = entityManager.criteriaBuilder
        val criteria = cb.createQuery(User::class.java)

        val user = criteria.from(User::class.java)
        criteria.select(user)

        val predicates = mutableListOf<Predicate>()

        filter.firstName?.let {
            predicates.add(cb.like(user.get("firstName"), it))
        }

        filter.lastName?.let {
            predicates.add(cb.like(user.get("lastName"), it))
        }

        filter.birthDate?.let {
            predicates.add(cb.equal(user.get<LocalDate>("birthDate"), it))
        }

        criteria.where(predicates)

        return entityManager
            .createQuery(criteria)
            .resultList
    }

    override fun findAllByFilterQueryDSL(filter: UserFilter): List<User> {
        val predicate = QPredicates.builder()
            .add(filter.firstName, user.firstName::containsIgnoreCase)
            .add(filter.lastName, user.lastName::containsIgnoreCase)
            .add(filter.birthDate, user.birthDate::eq)
            .build()
        return JPAQuery<User>(entityManager)
            .select(user)
            .from(user)
            .where(predicate)
            .fetch()
    }

}
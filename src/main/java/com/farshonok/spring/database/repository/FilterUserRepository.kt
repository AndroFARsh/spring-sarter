package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.User
import com.farshonok.spring.database.entities.User_
import com.farshonok.spring.dto.UserFilter
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.Predicate

interface FilterUserRepository {
    fun findAllByFilter(filter: UserFilter): List<User>
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

        filter.firsName?.let {
            predicates.add(cb.like(user.get(User_.firstName), it))
        }

        filter.lastName?.let {
            predicates.add(cb.like(user.get(User_.lastName), it))
        }

        filter.birthDate?.let {
            predicates.add(cb.equal(user.get(User_.birthDate), it))
        }

        criteria.where(predicates)

        return entityManager
            .createQuery(criteria)
            .resultList
    }

}
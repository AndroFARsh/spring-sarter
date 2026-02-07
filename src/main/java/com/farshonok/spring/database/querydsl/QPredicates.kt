package com.farshonok.spring.database.querydsl

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate

enum class Strategy {
    And,
    Or
}

class QPredicates {
    private val predicates: MutableList<Predicate> = mutableListOf()

    fun <T> add(value: T?, function: (T)->Predicate): QPredicates {
        if (value != null) {
            predicates.add(function(value))
        }
        return this
    }

    fun build(strategy: Strategy = Strategy.And) : Predicate? = when(strategy) {
        Strategy.And -> ExpressionUtils.allOf(predicates)
        Strategy.Or -> ExpressionUtils.anyOf(predicates)
    }

    companion object {
        fun builder() = QPredicates()
    }
}
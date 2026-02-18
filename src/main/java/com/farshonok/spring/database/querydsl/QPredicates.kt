package com.farshonok.spring.database.querydsl

import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.Expressions
import java.util.Optional

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

    fun build(strategy: Strategy = Strategy.And): Predicate =
        // in case of empty predicates list, we should return "true" predicate to avoid "where false" in query
        Optional.ofNullable(
            when (strategy) {
                Strategy.And -> ExpressionUtils.allOf(predicates)
                Strategy.Or -> ExpressionUtils.anyOf(predicates)
            }
        ).orElseGet { Expressions.asBoolean(true).isTrue }


    companion object {
        fun builder() = QPredicates()
    }
}
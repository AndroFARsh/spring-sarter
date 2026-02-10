package com.farshonok.spring.mappers

interface Mapper<F, T> {
    fun map(from: F): T

    fun map(from: F, to: T): T = to
}


package com.farshonok.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.contracts.ExperimentalContracts

@SpringBootApplication
class DemoApplication

@OptIn(ExperimentalContracts::class)
fun main(args: Array<String>) {
    val context = runApplication<DemoApplication>(*args)
    with(context) {
        println(this)
    }
}
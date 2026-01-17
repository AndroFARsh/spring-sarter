package com.farshonok.spring

import com.sun.org.apache.xalan.internal.xsltc.compiler.Closure
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@SpringBootApplication
@ConfigurationPropertiesScan
class DemoApplication

@OptIn(ExperimentalContracts::class)
fun main(args: Array<String>) {
    val context = runApplication<DemoApplication>(*args)
    with(context) {
        println(this)
    }
}
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

@ExperimentalContracts
fun isNotNull(value: Any?): Boolean {
    contract {
        returns(true) implies (value != null)
    }
    return value != null
}

@OptIn(ExperimentalContracts::class)
fun main(args: Array<String>) {
    val list = listOf(1,2, 3, 4, 5, 6)

    val nullObj: String? = null//"TEST"
    if (isNotNull(nullObj)) {
        val newString = nullObj.substring(0, 1)
        println(newString)
    }
//    val closure = {
//        println("this is a closure $this")
//    }



    val context = runApplication<DemoApplication>(*args)

    with(context) {

    }
    println(context)
}
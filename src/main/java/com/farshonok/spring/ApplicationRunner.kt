package com.farshonok.spring

import com.farshonok.spring.database.repository.UserRepository
import org.springframework.context.support.ClassPathXmlApplicationContext

fun main(args: Array<String>) {
    ClassPathXmlApplicationContext("application.xml").use { context ->
        val userRepository = context.getBean("userRepository", UserRepository::class.java)
        println(userRepository)
    }
}
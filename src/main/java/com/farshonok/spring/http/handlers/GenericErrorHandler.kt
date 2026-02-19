package com.farshonok.spring.http.handlers

import com.farshonok.spring.http.controllers.UsersController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice(basePackages = ["com.farshonok.spring.http.controllers"])
class GenericErrorHandler /*: ResponseEntityExceptionHandler*/ {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, model: Model) : String {
        log.error("Exception occurred", ex)
        return "errors/generic"
    }
}
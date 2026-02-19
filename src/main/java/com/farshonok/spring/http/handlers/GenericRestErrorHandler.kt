package com.farshonok.spring.http.handlers

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice(basePackages = ["com.farshonok.spring.http.rests"])
class GenericRestErrorHandler : ResponseEntityExceptionHandler()
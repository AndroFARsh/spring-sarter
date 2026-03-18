package com.farshonok.spring.http.controllers

import com.farshonok.spring.dto.CredentialsDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.SessionAttributes

@Controller
@SessionAttributes(value = ["credentials"])
class LoginController {

    @GetMapping("/login")
    fun loginPage(model: Model) = "users/login"
}
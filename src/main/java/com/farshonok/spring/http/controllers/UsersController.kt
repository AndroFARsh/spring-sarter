package com.farshonok.spring.http.controllers

import com.farshonok.spring.database.entities.Role
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.service.CompanyService
import com.farshonok.spring.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/users")
class UsersController(
    private val userService: UserService,
    private val companyService: CompanyService
) {

    // Create read all user
    @GetMapping
    fun findAll(model: Model) : String {
        val users = userService.findAll()
        model.addAttribute("users", users.sortedBy { it.id })
        return "users/list"
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, model: Model) : String =
        userService.findById(id)
            .map {
                model.addAttribute("user", it)
                model.addAttribute("roles", Role.entries.toTypedArray())
                model.addAttribute("companies",  companyService.findAll())
                "users/info"
            }
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }


    @GetMapping("/signup")
    fun signup(model: Model, @ModelAttribute("user") user: UserCreateEditDto) : String {
        model.addAttribute("user", user)
        model.addAttribute("roles", Role.entries.toTypedArray())
        model.addAttribute("companies",  companyService.findAll())
        return "users/signup"
    }

    // Create new user
    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    fun create(createUser: UserCreateEditDto, redirectAttributes: RedirectAttributes) : String {
        return if (!createUser.validate()) {
            redirectAttributes.addFlashAttribute("user", createUser)
            "redirect:/users/signup"
        } else {
            "redirect:/users/${userService.create(createUser).id}"
        }
    }

    private fun UserCreateEditDto.validate() : Boolean {
        return email != null &&
               firstName != null &&
               lastName != null &&
               role != null
    }

    // Update user
    // @PutMapping("/{id}")
    // html form doesn't support put method so use Post for now
    @PostMapping("/{id}/update")
    fun update(@PathVariable id: Int, user: UserCreateEditDto) : String {
        return userService.update(id, user)
            .map {
                "redirect:/users/${id}"
            }
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
            }
    }

    // Update user
    // @DeleteMapping("/{id}")
    // html form doesn't support delete method so use Post for now
    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Int) : String {
        if (!userService.delete(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }
        return "redirect:/users"
    }
}
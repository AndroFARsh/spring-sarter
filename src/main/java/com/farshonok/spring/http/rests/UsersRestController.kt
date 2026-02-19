package com.farshonok.spring.http.rests

import com.farshonok.spring.dto.PageResponse
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.dto.UserFilter
import com.farshonok.spring.service.CompanyService
import com.farshonok.spring.service.UserService
import com.farshonok.spring.validators.UserCreateAction
import com.farshonok.spring.validators.UserUpdateAction
import jakarta.validation.groups.Default
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/api/v1/users")
class UsersRestController(
    private val userService: UserService,
    private val companyService: CompanyService,
) {
    @GetMapping
    fun findAll(filter: UserFilter, pageable: Pageable) =
        PageResponse.of(userService.findAll(filter, pageable))

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int) =
        userService.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Validated(Default::class, UserCreateAction::class)
        @RequestBody
        user: UserCreateEditDto,
    ) = userService.create(user)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @Validated(Default::class, UserUpdateAction::class)
        @RequestBody
        user: UserCreateEditDto
    ) = userService.update(id, user)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
      if (!userService.delete(id)) {
          throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
      }
    }
}


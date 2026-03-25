package com.farshonok.spring.http.rests

import com.farshonok.spring.dto.PageResponse
import com.farshonok.spring.dto.UserCreateEditDto
import com.farshonok.spring.dto.UserFilter
import com.farshonok.spring.service.CompanyService
import com.farshonok.spring.service.UserService
import com.farshonok.spring.validators.UserCreateAction
import com.farshonok.spring.validators.UserUpdateAction
import jakarta.validation.groups.Default
import org.springframework.cglib.core.Local
import org.springframework.context.MessageSource
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.Locale

@RestController
@RequestMapping("/api/v1/string")
class StringRestController(
    val messageSource: MessageSource
) {
    @GetMapping
    fun resolveString(@RequestParam key: String, @RequestParam(defaultValue = "") lang: String) =
        messageSource.getMessage(key, null, null, Locale.of(lang))

}


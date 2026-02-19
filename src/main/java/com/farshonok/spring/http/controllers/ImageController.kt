package com.farshonok.spring.http.controllers

import com.farshonok.spring.service.ImageService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.*
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/image")
class ImageController(
    private val imageService: ImageService,
) {
    @ResponseBody
    @GetMapping("/{name}")
    fun findByName(@PathVariable name: String) =
        imageService[name]
            .map {
                ResponseEntity.ok()
                    .header(CONTENT_TYPE, APPLICATION_OCTET_STREAM_VALUE)
                    .contentLength(it.size.toLong())
                    .body(it)
            }.orElseGet(ResponseEntity.notFound()::build)

}


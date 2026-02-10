package com.farshonok.spring.http.controllers.integrations

import com.farshonok.spring.BaseIntegrationTest
import com.farshonok.spring.dto.UserReadDto
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@AutoConfigureMockMvc
class UsersControllerTest(
    val mockMvc: MockMvc
) : BaseIntegrationTest() {
    @Test
    fun find_all() {
        mockMvc.perform(get("/users"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("users/list"))
            .andExpect(model().attributeExists("users"))
            .andExpect(model().attribute("users", hasSize<UserReadDto>(5)))
    }

    @Test
    fun create() {
        mockMvc.perform(
            post("/users")
                .param("email", "test@test.com")
                .param("firstName", "TestName")
                .param("lastName", "TestLastname")
                .param("birthDate", "1986-01-01")
                .param("role", "ADMIN")
        ).andExpectAll(
            status().is3xxRedirection,
            redirectedUrlPattern("/users/{\\d+}"),
        )
    }

}
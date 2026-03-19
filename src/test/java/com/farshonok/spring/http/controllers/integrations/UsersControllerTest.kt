package com.farshonok.spring.http.controllers.integrations

import com.farshonok.spring.BaseIntegrationTest
import com.farshonok.spring.database.entities.Role
import org.junit.jupiter.api.Test
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@AutoConfigureMockMvc
class UsersControllerTest(
    val mockMvc: MockMvc
) : BaseIntegrationTest() {

    @Test
    //@WithMockUser(username = "test@gmail.com", authorities = ["ADMIN"])
    fun find_all() {
        mockMvc.perform(get("/users").with( user("test@gmail.com").authorities(Role.ADMIN)))
            .andExpect(status().is2xxSuccessful)
            .andExpect(view().name("users/list"))
            .andExpect(model().attributeExists("users"))
//            .andExpect(model().attribute("users.content", hasSize<UserReadDto>(5)))
    }

    @Test
    fun create() {
        mockMvc.perform(
            post("/users")
                .with(csrf())
                .param("email", "test@test.com")
                .param("rawPassword", "123")
                .param("firstName", "TestName")
                .param("lastName", "TestLastname")
                .param("birthDate", "1986-01-01")
                .param("role", "ADMIN")
        ).andExpectAll(
            status().is3xxRedirection,
            redirectedUrl("/login")
        )
    }

}
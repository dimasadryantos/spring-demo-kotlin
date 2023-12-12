package com.example.springdemokotlin.test

import com.example.springdemokotlin.test.config.ComponentTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ComponentTest
class TestExample @Autowired constructor(
    private val mockMvc: MockMvc,
) {
    @Test
    fun `given invalid uri then return 404`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/invalidURI/"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `given valid uri then return 200`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/validURI/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}

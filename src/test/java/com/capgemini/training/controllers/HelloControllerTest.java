package com.capgemini.training.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {


    @Autowired
    public MockMvc mockMvc;

    @Test
    void shouldGetHelloWorld() throws Exception {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Hello World"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
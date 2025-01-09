package com.sparta.nalda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nalda.dto.user.SignupRequestDto;
import com.sparta.nalda.util.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserTest {

    private static final String BASE_URL = "/user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signup() {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto(
            "test@test.com",
            "password",
            "address",
            UserRole.CUSTOMER);




        //when

        //then
    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void deleteUser() {
    }
}
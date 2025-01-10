package com.sparta.nalda.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.user.SignupRequestDto;
import com.sparta.nalda.util.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
        ResultActions result = this.mockMvc.perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequestDto)))
        );

        //then
        String contentAsString = result.andReturn().getResponse().getContentAsString();
        MessageResponse actualResult = this.objectMapper.readValue(contentAsString, MessageResponse.class);

        result.andExpect(status().isOk());

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
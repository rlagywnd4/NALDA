package com.sparta.nalda.controller;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nalda.common.MessageResponse;
import com.sparta.nalda.dto.user.SignupRequestDto;
import com.sparta.nalda.entity.UserEntity;
import com.sparta.nalda.repository.UserRepository;
import com.sparta.nalda.util.AuthUser;
import com.sparta.nalda.util.UserRole;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserTest {

    private static final String BASE_URL = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private SignupRequestDto userRequest;

    @Autowired
    private UserRepository userRepository;

    private MockedStatic<AuthUser> mockAuthUser;


    @BeforeEach
    void setUp() {

        mockAuthUser = mockStatic(AuthUser.class);

        UserEntity user = new UserEntity(
            "test@test.com",
            passwordEncoder.encode("1234!ABcd"),
            "address",
            UserRole.CUSTOMER
        );

        UserEntity savedUser = userRepository.save(user);

        mockAuthUser.when(AuthUser::getId).thenReturn(savedUser.getId());

        userRequest = new SignupRequestDto(
            "tes1t@test.com",
            "1234!ABcd",
            "address",
            UserRole.CUSTOMER
        );
    }

    @AfterEach
    void delete() {
        if (mockAuthUser != null) {
            mockAuthUser.close();
        }
        userRepository.deleteAll();
    }


    @Test
    void signup() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        );

        //then
        String contentAsString = result.andReturn().getResponse().getContentAsString();
        MessageResponse actualResult = objectMapper.readValue(contentAsString, MessageResponse.class);

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("회원가입에 성공하였습니다."));
    }

    @Test
    void getUser() throws Exception {
        //when
        ResultActions result = mockMvc.perform(
            get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk());
    }


    @Test
    void updateUser() throws Exception {
        //given
        String updatedAddress = "새로운 주소";

        //when
        ResultActions result = mockMvc.perform(
            patch(BASE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("address", updatedAddress)
        );

        //then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("주소 수정이 완료되었습니다."));

        UserEntity updatedUser = userRepository.findById(AuthUser.getId()).orElseThrow();
        assertThat(updatedUser.getAddress()).isEqualTo(updatedAddress);
    }

    @Test
    void updatePassword() throws Exception {
        //given
        String oldPassword = "1234!ABcd";
        String newPassword = "1234!ABcd1";
        Map<String, String> updateRequest = new HashMap<>();
        updateRequest.put("password", oldPassword);
        updateRequest.put("newPassword", newPassword);

        //when
        ResultActions result = mockMvc.perform(
            patch(BASE_URL + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))
        );

        //then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("비밀번호 수정이 완료되었습니다."));

    }

    @Test
    void deleteUser() throws Exception {
        //given
        String password = "1234!ABcd";

        // when
        ResultActions result = mockMvc.perform(
            MockMvcRequestBuilders.delete(BASE_URL)
                .param("password", password)
        );

        // then
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("유저 삭제가 완료되었습니다."));

        // Verify the user is deleted from the database
        boolean userExists = userRepository.existsById(AuthUser.getId());
        assertThat(userExists).isFalse();
    }

}

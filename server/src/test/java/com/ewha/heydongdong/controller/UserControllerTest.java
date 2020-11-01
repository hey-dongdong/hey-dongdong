package com.ewha.heydongdong.controller;

import com.ewha.heydongdong.model.dto.UserSignUpDto;
import com.ewha.heydongdong.model.protocol.Header;
import com.ewha.heydongdong.model.protocol.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("User sign up submit | Success")
    void signUpSubmit_Success() throws Exception {
        String content = objectMapper.writeValueAsString(new Request(
                new Header("SignUpRequest", "new_user"),
                objectMapper.valueToTree(UserSignUpDto.builder()
                        .userId("new_user")
                        .email("new_email@email.com")
                        .password("new_password")
                        .phone("010-5555-6666")
                        .userName("김익명")
                        .build())
        ));

        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("new_user"));
    }

    @Test
    @DisplayName("User sign up submit | Fail : Duplicate userId")
    void signUpSubmit_Fail_DuplicateUserId() throws Exception {
        String content = objectMapper.writeValueAsString(new Request(
                new Header("SignUpRequest", "test_user"),
                objectMapper.valueToTree(UserSignUpDto.builder()
                        .userId("test_user")
                        .email("new_email@email.com")
                        .password("new_password")
                        .phone("010-5555-6666")
                        .userName("김익명")
                        .build())
        ));

        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("900 Duplicate User [userId=test_user]"));
    }

    @Test
    @DisplayName("User sign up submit | Fail : Duplicate email")
    void signUpSubmit_Fail_DuplicateEmail() throws Exception {
        String content = objectMapper.writeValueAsString(new Request(
                new Header("SignUpRequest", "new_user"),
                objectMapper.valueToTree(UserSignUpDto.builder()
                        .userId("new_user")
                        .email("email@email.com")
                        .password("new_password")
                        .phone("010-5555-6666")
                        .userName("김익명")
                        .build())
        ));

        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("900 Duplicate User [email=email@email.com]"));
    }
}
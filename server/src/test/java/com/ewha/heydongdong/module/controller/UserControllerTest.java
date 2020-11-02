package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.UserSignUpDto;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.module.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("User sign up submit | Success")
    void signUpSubmit_Success() throws Exception {
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignUpRequest", "new_user"),
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
                new RequestHeader("SignUpRequest", "test_user"),
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
                new RequestHeader("SignUpRequest", "new_user"),
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
                .andExpect(content().string(
                        "DuplicateUserError: Duplicate User [email=email@email.com]"
                ));
    }

    @Test
    @DisplayName("Password encoding | Success")
    void passwordEncoding_Success() throws Exception {

        // Given
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignUpRequest", "new_user"),
                objectMapper.valueToTree(UserSignUpDto.builder()
                        .userId("new_user")
                        .email("new_email@email.com")
                        .password("new_password")
                        .phone("010-5555-6666")
                        .userName("김익명")
                        .build())
        ));

        // When
        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        User user = userRepository.findById("new_user").get();

        // Then
        assertNotNull(user);
        assertNotEquals(user.getPassword(), "new_password");
    }

    @Test
    @DisplayName("Check email token | Success")
    void checkEmailToken_Success() throws Exception {

        mockMvc.perform(get("/user/check-email-token/new_email2@email.com/26b4e398-50b9-4c5f-bc4a-de60b9c9e21b")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("new_user3"));
    }

    @Test
    @DisplayName("Check email token | Fail : No such email")
    void checkEmailToken_Fail_NoSuchEmail() throws Exception {

        mockMvc.perform(get("/user/check-email-token/no_email@email.com/26b4e398-50b9-4c5f-bc4a-de60b9c9e21b")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("InvalidRequestParameterError: Invalid request parameter [No such email=no_email@email.com]"));
    }

    @Test
    @DisplayName("Check email token | Fail : Wrong token")
    void checkEmailToken_Fail_WrongToken() throws Exception {

        mockMvc.perform(get("/user/check-email-token/new_email2@email.com/wrong-token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("InvalidRequestParameterError: Invalid request parameter [Wrong token]"));
    }
}

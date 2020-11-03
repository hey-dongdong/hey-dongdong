package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.UserSignUpDto;
import com.ewha.heydongdong.module.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("SignUpResponse")
                        .message("new_user")
                        .build())
                .build();

        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
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

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("DuplicateUserError")
                        .message("DuplicateUserError: Duplicate User [userId=test_user]")
                        .build())
                .build();

        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
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

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("DuplicateUserError")
                        .message("DuplicateUserError: Duplicate User [email=email@email.com]")
                        .build())
                .build();

        mockMvc.perform(post("/user/sign-up")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
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

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("CheckEmailTokenResponse")
                        .message("test_user")
                        .build())
                .build();

        mockMvc.perform(get("/user/check-email-token/email@email.com/26b4e398-50b9-4c5f-bc4a-de60b9c9e21b")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Check email token | Fail : No such email")
    void checkEmailToken_Fail_NoSuchEmail() throws Exception {

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestParameterError")
                        .message("InvalidRequestParameterError: Invalid request parameter [No such email=no_email@email.com]")
                        .build())
                .build();

        mockMvc.perform(get("/user/check-email-token/no_email@email.com/26b4e398-50b9-4c5f-bc4a-de60b9c9e21b")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Check email token | Fail : Wrong token")
    void checkEmailToken_Fail_WrongToken() throws Exception {

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestParameterError")
                        .message("InvalidRequestParameterError: Invalid request parameter [Wrong token]")
                        .build())
                .build();

        mockMvc.perform(get("/user/check-email-token/email@email.com/wrong-token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("User sign in submit | Success")
    void signInSubmit_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "test_user");
        payload.put("password", "new_password");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignInRequest", "test_user"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("SignInResponse")
                        .message("test_user")
                        .build())
                .build();

        mockMvc.perform(post("/user/sign-in")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("User sign in submit | Fail : No such user")
    void signInSubmit_Fail_NoSuchUser() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "no_user");
        payload.put("password", "new_password");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignInRequest", "no_user"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("NoSuchUserError")
                        .message("NoSuchUserError: No such user [userId=no_user]]")
                        .build())
                .build();

        mockMvc.perform(post("/user/sign-in")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Find user id | Success")
    void findUserId_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("email", "email@email.com");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("FindIdRequest", "N/A"), payload));

        payload.removeAll();
        payload.put("userId", "test_user");
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("FindIdResponse")
                        .message("email@email.com")
                        .build())
                .payload(payload)
                .build();

        mockMvc.perform(post("/user/find-info/id")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Find user id | Fail : No such user")
    void findUserId_Fail_NoSuchUser() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("email", "no_email@email.com");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("FindIdRequest", "N/A"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("NoResultFromDBError")
                        .message("NoResultFromDBError [No user for email=no_email@email.com]")
                        .build())
                .build();

        mockMvc.perform(post("/user/find-info/id")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }
}
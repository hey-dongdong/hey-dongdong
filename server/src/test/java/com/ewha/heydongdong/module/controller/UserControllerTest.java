package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.UserSignUpDto;
import com.ewha.heydongdong.module.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("User sign in | Success")
    void signInSubmit_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "tester");
        payload.put("password", "tester");
        payload.put("deviceToken", "someTokenValue");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignInRequest", "tester"), payload));

        mockMvc.perform(post("/user/sign-in")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());    // token값 때문에 Postman으로 reponse 확인 필요
    }

    @Test
    @DisplayName("User sign in | Fail : Wrong input")
    void signInSubmit_Fail_NoSuchUser() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "no_user");
        payload.put("password", "new_password");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignInRequest", "no_user"), payload));

        mockMvc.perform(post("/user/sign-in")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("User sign in | Fail : Email not verified")
    void signInSubmit_Fail_NotVerified() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "ewha111");
        payload.put("password", "111");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("SignInRequest", "ewha111"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("NotVerifiedUserException")
                        .message("Email not verified [userId=ewha111]")
                        .build())
                .build();
        mockMvc.perform(post("/user/sign-in")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }


    @Test
    @DisplayName("Find user id | Success")
    void findUserId_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("email", "email@email.com");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("FindIdRequest", "N/A"), payload));

        ObjectNode responsePayload = objectMapper.createObjectNode();
        responsePayload.put("userId", "test_user");
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("FindIdResponse")
                        .message("email@email.com")
                        .build())
                .payload(responsePayload)
                .build();

        mockMvc.perform(post("/user/find-info/id")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Find user id | Fail : No user found")
    void findUserId_Fail_NoSuchUser() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("email", "no_email@email.com");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("FindIdRequest", "N/A"), payload));

        mockMvc.perform(post("/user/find-info/id")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Find user pw | Success")
    void findUserPw_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "test_user");
        payload.put("userName", "김익명");
        payload.put("email", "email@email.com");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("FindPwRequest", "test_user"), payload));

        mockMvc.perform(post("/user/find-info/pw")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Find user pw | Fail : No user found")
    void findUserPw_Fail_NoSuchUser() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "no_user");
        payload.put("userName", "김익명");
        payload.put("email", "email@email.com");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("FindPwRequest", "no_user"), payload));

        mockMvc.perform(post("/user/find-info/pw")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Change pw | Success")
    void changePw_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "test_user");
        payload.put("newPw", "test_password");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("ChangePwRequest", "test_user"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("ChangePwResponse")
                        .message("test_user")
                        .build())
                .build();

        mockMvc.perform(post("/user/change-pw")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));

        User user = userRepository.getOne("test_user");
        assertTrue(passwordEncoder.matches("test_password", user.getPassword()));
    }

    @Test
    @DisplayName("Change pw | Fail : No such user")
    void changePw_Fail_NoSuchUser() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("userId", "no_user");
        payload.put("newPw", "test_password");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("ChangePwRequest", "no_user"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [userId=no_user]")
                        .build())
                .build();

        mockMvc.perform(post("/user/change-pw")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }


    @Test
    @DisplayName("Get no show count | Success")
    void getNoShowCount_Success() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetNoShowCountRequest", "test_user"), null));

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("noShowCount", "0");
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("GetNoShowCountResponse")
                        .message("test_user")
                        .build())
                .payload(objectMapper.valueToTree(payload))
                .build();

        mockMvc.perform(post("/user/no-show-count")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Get no show count | Fail : No such user")
    void getNoShowCount_Fail_NoSuchUser() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetNoShowCountRequest", "no_user"), null));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [userId=no_user]")
                        .build())
                .build();

        mockMvc.perform(post("/user/no-show-count")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }


    @Test
    @DisplayName("User sign up | Success")
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
    @DisplayName("User sign up | Fail : Duplicate userId")
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
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("User sign up | Fail : Duplicate email")
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
                .andExpect(status().isNoContent());
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

        mockMvc.perform(get("/user/check-email-token/email@email.com/cc17c1f4-5192-4ede-b1a6-4c1d4dd22d42")
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
                        .name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [email=no_email@email.com]")
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
                        .name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [Wrong email token]")
                        .build())
                .build();

        mockMvc.perform(get("/user/check-email-token/email@email.com/wrong-token")
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
}

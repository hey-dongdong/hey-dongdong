package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.DuplicateUserException;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.exception.NoSuchUserException;
import com.ewha.heydongdong.infra.jwt.JwtTokenProvider;
import com.ewha.heydongdong.infra.mail.EmailMessage;
import com.ewha.heydongdong.infra.mail.EmailService;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.UserSignUpDto;
import com.ewha.heydongdong.module.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JsonBuilder jsonBuilder;

    public String signUp(JsonNode payload) throws JsonProcessingException {
        User newUser = saveNewUser(payload);
        emailService.sendEmail(generateSignUpVerificationEmail(newUser));
        return jsonBuilder.buildJsonWithHeader("SignUpResponse", newUser.getUserId());
    }

    private User saveNewUser(JsonNode payload) throws JsonProcessingException {
        UserSignUpDto userSignUpDto = objectMapper.treeToValue(payload, UserSignUpDto.class);
        userSignUpDto.validateFieldsNotNull();
        validateDuplicateUser(userSignUpDto);
        return userRepository.save(buildUserFromDto(userSignUpDto));
    }

    private void validateDuplicateUser(UserSignUpDto userSignUpDto) {
        validateDuplicateId(userSignUpDto.getUserId());
        validateDuplicateEmail(userSignUpDto.getEmail());
    }

    private void validateDuplicateId(String userId) {
        List<User> foundUsers = userRepository.findByUserId(userId);
        if (!foundUsers.isEmpty())
            throw new DuplicateUserException("userId=" + userId);
    }

    private void validateDuplicateEmail(String email) {
        List<User> foundUsers = userRepository.findByEmail(email);
        if (!foundUsers.isEmpty())
            throw new DuplicateUserException("email=" + email);
    }

    private User buildUserFromDto(UserSignUpDto userSignUpDto) {
        return User.builder()
                .userId(userSignUpDto.getUserId())
                .userName(userSignUpDto.getUserName())
                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                .email(userSignUpDto.getEmail())
                .phone(userSignUpDto.getPhone())
                .banAt(null)
                .noShowCount(0)
                .isEmailVerified(false)
                .emailCheckToken(UUID.randomUUID().toString())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    private EmailMessage generateSignUpVerificationEmail(User newUser) {
        Context context = new Context();
        context.setVariable("link", "/user/check-email-token/" + newUser.getEmail() + "/" + newUser.getEmailCheckToken());
        context.setVariable("userName", newUser.getUserName());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "헤이동동 서비스를 사용하려면 링크를 클릭하세요.");
        context.setVariable("host", "http://localhost:8080");   // TODO [지우] 서버 주소 변경

        String message = templateEngine.process("verify-email-on-sign-up", context);

        return EmailMessage.builder()
                .to(newUser.getEmail())
                .subject("헤이동동 회원 가입을 위한 인증메일입니다.")
                .message(message)
                .build();
    }

    public String checkEmailToken(String email, String emailCheckToken) {
        User user = checkIfUserExists(email);
        if (user.getEmailCheckToken().equals(emailCheckToken))
            updateUserEmailVerifiedOnDB(user);
        else
            throw new InvalidRequestParameterException("Wrong email token");
        return jsonBuilder.buildJsonWithHeader("CheckEmailTokenResponse", user.getUserId());
    }

    private User checkIfUserExists(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty())
            throw new InvalidRequestParameterException("No such email=" + email);
        else
            return users.get(0);
    }

    private void updateUserEmailVerifiedOnDB(User user) {
        user.setIsEmailVerified(true);
        userRepository.save(user);
    }

    public String signIn(JsonNode payload) {
        User given = buildUserFromJson(payload);
        User expected = findOptionalUserFromDB(given.getUserId());
        checkPassword(given, expected);
        String newToken = jwtTokenProvider.createJwtToken(expected.getUsername(), expected.getRoles());
        return buildUserSignInJsonResponse(given.getUserId(), newToken);
    }

    private User buildUserFromJson(JsonNode payload) {
        return User.builder()
                .userId(payload.get("userId").asText())
                .password(payload.get("password").asText())
                .build();
    }

    private User findOptionalUserFromDB(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoResultFromDBException("userId=" + userId));
    }

    private void checkPassword(User given, User expected) {
        if (!passwordEncoder.matches(given.getPassword(), expected.getPassword()))
            throw new NoResultFromDBException("Failed sign-in userId=" + given.getUserId());
    }

    private String buildUserSignInJsonResponse(String userId, String token) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("SignInResponse", userId),
                jsonBuilder.buildResponsePayload("token", token)
        );
    }

    public String findUserId(JsonNode payload) {
        User user = findUserByEmail(payload.get("email").asText());
        return buildUserIdJsonResponse(user);
    }

    private User findUserByEmail(String email) {
        List<User> foundUsers = userRepository.findByEmail(email);
        if (foundUsers.isEmpty())
            throw new NoResultFromDBException("No user for email=" + email);
        return foundUsers.get(0);
    }

    private String buildUserIdJsonResponse(User user) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("FindIdResponse", user.getEmail()),
                jsonBuilder.buildResponsePayload("userId", user.getUserId())
        );
    }

    public String findUserPw(JsonNode payload) {
        User user = findOptionalUserFromDB(payload.get("userId").asText());
        validateGivenInfo(payload, user);
        refreshEmailCheckToken(user);
        emailService.sendEmail(generateLoginEmail(user));
        return jsonBuilder.buildJsonWithHeader("FindPwResponse", user.getUserId());
    }

    private void validateGivenInfo(JsonNode given, User expected) {
        if (!given.get("userId").asText().equals(expected.getUserId())
                | !given.get("userName").asText().equals(expected.getUserName())
                | !given.get("email").asText().equals(expected.getEmail())) {
            throw new NoResultFromDBException("No user for userId=" + expected.getUserId());
        }
    }

    private void refreshEmailCheckToken(User user) {
        user.setEmailCheckToken(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    private EmailMessage generateLoginEmail(User user) {
        Context context = new Context();
        context.setVariable("link", "/user/login-by-email/" + user.getEmail() + "/" + user.getEmailCheckToken());
        context.setVariable("userName", user.getUserName());
        context.setVariable("linkName", "로그인 링크");
        context.setVariable("message", "헤이동동에 로그인하려면 링크를 클릭하세요.");
        context.setVariable("host", "http://localhost:8080");   // TODO [지우] 서버 주소 변경

        String message = templateEngine.process("login-by-email", context);
        return EmailMessage.builder()
                .to(user.getEmail())
                .subject("헤이동동 로그인을 위한 링크메일입니다.")
                .message(message)
                .build();
    }

    public String loginByEmail(String email, String emailCheckToken) {
        User user = checkIfUserExists(email);
        if (!user.getEmailCheckToken().equals(emailCheckToken))
            throw new InvalidRequestParameterException("Wrong email token");
        return jsonBuilder.buildJsonWithHeader("LoginByEmailResponse", user.getUserId());
    }

    public String changePw(JsonNode payload) {
        User user = findRequiredUserFromDB(payload.get("userId").asText());
        updateUserPwOnDB(payload.get("newPw").asText(), user);
        return jsonBuilder.buildJsonWithHeader("ChangePwResponse", user.getUserId());
    }

    private void updateUserPwOnDB(String newPw, User user) {
        user.setPassword(passwordEncoder.encode(newPw));
        userRepository.save(user);
    }

    public String getUserNoShowCount(String userId) {
        User user = findRequiredUserFromDB(userId);
        return buildUserNoShowCountJsonResponse(user);
    }

    private User findRequiredUserFromDB(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("userId=" + userId));
    }

    private String buildUserNoShowCountJsonResponse(User user) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetNoShowCountResponse", user.getUserId()),
                jsonBuilder.buildResponsePayload("noShowCount", user.getNoShowCount())
        );
    }
}

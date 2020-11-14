package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.*;
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


    public String signIn(JsonNode payload) {
        User givenUser = buildUserFromJson(payload);
        User expectedUser = findOptionalUserById(givenUser.getUserId());
        checkIfEmailVerified(expectedUser);
        checkPassword(givenUser, expectedUser);
        saveDeviceToken(expectedUser, payload.get("deviceToken").asText());
        String userToken = jwtTokenProvider.createJwtToken(expectedUser.getUsername(), expectedUser.getRoles());
        return buildUserSignInJsonResponse(expectedUser, userToken);
    }

    private User buildUserFromJson(JsonNode payload) {
        return User.builder()
                .userId(payload.get("userId").asText())
                .password(payload.get("password").asText())
                .build();
    }

    private User findOptionalUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoResultFromDBException("userId=" + userId));
    }

    private void checkIfEmailVerified(User givenUser) {
        if (!givenUser.getIsEmailVerified())
            throw new NotVerifiedUserException(givenUser.getUserId());
    }

    private void checkPassword(User givenUser, User expectedUser) {
        if (!passwordEncoder.matches(givenUser.getPassword(), expectedUser.getPassword()))
            throw new NoResultFromDBException("Failed sign-in userId=" + givenUser.getUserId());
    }

    private void saveDeviceToken(User expectedUser, String deviceToken) {
        expectedUser.setDeviceToken(deviceToken);
        userRepository.save(expectedUser);
    }

    private String buildUserSignInJsonResponse(User user, String token) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("SignInResponse", user.getUserId()),
                jsonBuilder.buildResponsePayloadFromText(new String[]{"token", "userName"}, new String[]{token, user.getUserName()})
        );
    }


    public String findUserId(JsonNode payload) {
        User user = findOptionalUserByEmail(payload.get("email").asText());
        return buildUserIdJsonResponse(user);
    }

    private User findOptionalUserByEmail(String email) {
        List<User> foundUsers = userRepository.findByEmail(email);
        if (foundUsers.isEmpty())
            throw new NoResultFromDBException("No user for email=" + email);
        return foundUsers.get(0);
    }

    private String buildUserIdJsonResponse(User user) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("FindIdResponse", user.getEmail()),
                jsonBuilder.buildResponsePayloadFromText("userId", user.getUserId())
        );
    }


    public String findUserPw(JsonNode payload) {
        User user = findOptionalUserById(payload.get("userId").asText());
        checkGivenUserInfo(payload, user);
        String tempPw = generateTempPw();
        saveUserPw(user, tempPw);
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("FindPwResponse", user.getUserId()),
                jsonBuilder.buildResponsePayloadFromText("tempPw", tempPw)
        );
    }

    private void checkGivenUserInfo(JsonNode givenUser, User expectedUser) {
        if (!givenUser.get("userId").asText().equals(expectedUser.getUserId())
                | !givenUser.get("userName").asText().equals(expectedUser.getUserName())
                | !givenUser.get("email").asText().equals(expectedUser.getEmail())) {
            throw new NoResultFromDBException("No user for userId=" + expectedUser.getUserId());
        }
    }

    private String generateTempPw() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 10);
    }

    private void saveUserPw(User user, String newPw) {
        user.setPassword(passwordEncoder.encode(newPw));
        userRepository.save(user);
    }


    public String changePw(JsonNode payload) {
        User user = findRequiredUserById(payload.get("userId").asText());
        String newPw = payload.get("newPw").asText();
        saveUserPw(user, newPw);
        return jsonBuilder.buildJsonWithHeader("ChangePwResponse", user.getUserId());
    }


    public String getUserNoShowCount(String userId) {
        User user = findRequiredUserById(userId);
        return buildUserNoShowCountJsonResponse(user);
    }

    private User findRequiredUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException("userId=" + userId));
    }

    private String buildUserNoShowCountJsonResponse(User user) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetNoShowCountResponse", user.getUserId()),
                jsonBuilder.buildResponsePayloadFromText("noShowCount", user.getNoShowCount())
        );
    }


    public String signUp(JsonNode payload) throws JsonProcessingException {
        User newUser = saveNewUser(payload);
        emailService.sendEmail(generateSignUpVerificationEmail(newUser));
        return jsonBuilder.buildJsonWithHeader("SignUpResponse", newUser.getUserId());
    }

    private User saveNewUser(JsonNode payload) throws JsonProcessingException {
        UserSignUpDto userSignUpDto = objectMapper.treeToValue(payload, UserSignUpDto.class);
        userSignUpDto.validateFieldsNotNull();
        checkDuplicateUser(userSignUpDto);
        return userRepository.save(buildUserFromUserSignUpDto(userSignUpDto));
    }

    private void checkDuplicateUser(UserSignUpDto userSignUpDto) {
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

    private User buildUserFromUserSignUpDto(UserSignUpDto userSignUpDto) {
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


    public String checkEmailToken(String email, String givenEmailCheckToken) {
        User user = findRequiredUserByEmail(email);
        checkIfTokenValid(givenEmailCheckToken, user.getEmailCheckToken());
        saveUserEmailVerified(user);
        return jsonBuilder.buildJsonWithHeader("CheckEmailTokenResponse", user.getUserId());
    }

    private User findRequiredUserByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty())
            throw new NoSuchUserException("email=" + email);
        else
            return users.get(0);
    }

    private void checkIfTokenValid(String givenToken, String requiredToken) {
        if (!givenToken.equals(requiredToken))
            throw new InvalidRequestParameterException("Wrong email token");
    }

    private void saveUserEmailVerified(User user) {
        user.setIsEmailVerified(true);
        userRepository.save(user);
    }


    public String getUserDeviceToken(User user) {
        User foundUser = findRequiredUserById(user.getUserId());
        return foundUser.getDeviceToken();
    }
}

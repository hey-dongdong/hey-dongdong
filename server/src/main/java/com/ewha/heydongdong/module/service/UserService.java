package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.exception.DuplicateUserException;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.exception.NoSuchUserException;
import com.ewha.heydongdong.infra.mail.EmailMessage;
import com.ewha.heydongdong.infra.mail.EmailService;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.UserSignUpDto;
import com.ewha.heydongdong.module.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;
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

    public String signUp(JsonNode payload) throws JsonProcessingException {
        User newUser = saveNewUser(payload);
        sendVerifyEmail(newUser);
        return newUser.getUserId();
    }

    private User saveNewUser(JsonNode payload) throws JsonProcessingException {
        UserSignUpDto userSignUpDto = objectMapper.treeToValue(payload, UserSignUpDto.class);
        userSignUpDto.validate();
        validateDuplicateUser(userSignUpDto);
        return userRepository.save(buildUser(userSignUpDto));
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

    private User buildUser(UserSignUpDto userSignUpDto) {
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
                .build();
    }

    private void sendVerifyEmail(User newUser) {

        emailService.sendEmail(generateVerifyEmail(newUser));
    }

    private EmailMessage generateVerifyEmail(User newUser) {
        Context context = new Context();
        context.setVariable("link", "/check-email-token/" + newUser.getEmail() + "/" + newUser.getEmailCheckToken());
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
        if (user.getEmailCheckToken().equals(emailCheckToken)) {
            user.setIsEmailVerified(true);
            return userRepository.save(user).getUserId();
        } else
            throw new InvalidRequestParameterException("Wrong token");
    }

    private User checkIfUserExists(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users.isEmpty())
            throw new InvalidRequestParameterException("No such email=" + email);
        else
            return users.get(0);
    }

    public String signIn(JsonNode payload) {
        User given = buildUserFromJson(payload);
        User expected = findUserFromDB(given.getUserId());
        if (passwordEncoder.matches(given.getPassword(), expected.getPassword()))
            return given.getUserId();
        else
            throw new NoSuchUserException("userId=" + given.getUserId() + "]");
    }

    private User buildUserFromJson(JsonNode payload) {
        return User.builder()
                .userId(payload.get("userId").asText())
                .password(payload.get("password").asText())
                .build();
    }

    private User findUserFromDB(String userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty())
            throw new NoSuchUserException("userId=" + userId + "]");
        return foundUser.get();
    }

    public String getUserNoShowCount(String userId) {
        List<User> foundUsers = userRepository.findByUserId(userId);
        if (foundUsers.isEmpty())
            throw new NoResultFromDBException("No such user for userId=" + userId);
        int noShowCount = foundUsers.get(0).getNoShowCount();

        return buildJsonResponse("GetNoShowCountResponse", userId, noShowCount);
    }

    private String buildJsonResponse(String responseName, String userId, Integer noShowCount) {
        ResponseHeader header = new ResponseHeader(responseName, userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("noShowCount", objectMapper.valueToTree(noShowCount));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }
}

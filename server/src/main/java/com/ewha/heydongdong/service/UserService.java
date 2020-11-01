package com.ewha.heydongdong.service;

import com.ewha.heydongdong.ConsoleMailSender;
import com.ewha.heydongdong.model.domain.User;
import com.ewha.heydongdong.model.dto.UserSignUpDto;
import com.ewha.heydongdong.model.exception.DuplicateUserException;
import com.ewha.heydongdong.model.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConsoleMailSender consoleMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .emailCheckToken(User.generateEmailCheckToken())
                .build();
    }

    private void sendVerifyEmail(User newUser) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newUser.getEmail());
        mailMessage.setSubject("헤이동동 회원 가입을 위한 인증 메일입니다.");
        mailMessage.setText("/check-email-token/" + newUser.getEmail() + "/" + newUser.getEmailCheckToken());
        consoleMailSender.send(mailMessage);
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
}

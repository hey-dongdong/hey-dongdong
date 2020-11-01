package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.User;
import com.ewha.heydongdong.model.dto.UserSignUpDto;
import com.ewha.heydongdong.model.exception.DuplicateUserException;
import com.ewha.heydongdong.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String signUp(JsonNode payload) throws JsonProcessingException {
        UserSignUpDto userSignUpDto = objectMapper.treeToValue(payload, UserSignUpDto.class);
        userSignUpDto.validate();
        validateDuplicateUser(userSignUpDto);
        User newUser = userRepository.save(buildUser(userSignUpDto));
        return newUser.getUserId();
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
                .password(userSignUpDto.getPassword())
                .email(userSignUpDto.getEmail())
                .phone(userSignUpDto.getPhone())
                .banAt(null)
                .noShowCount(0)
                .build();
    }
}

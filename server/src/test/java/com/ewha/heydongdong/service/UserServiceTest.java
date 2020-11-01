package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.User;
import com.ewha.heydongdong.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("User Service Test")
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Find User | Success")
    void findUser_Success() {

        // Given
        User newUser = User.builder()
                .userId("kim")
                .userName("김지우")
                .phone("010-0000-1111")
                .email("this@email.com")
                .noShowCount(0)
                .build();

        userRepository.save(newUser);

        // When
        List<User> users = userRepository.findByEmail(newUser.getEmail());

        // Then
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(newUser.getUserName(), users.get(0).getUserName());
    }

    // TODO [지우] 회원가입 테스트
    @Test
    @DisplayName("User Sign Up | Success")
    void signUp_Success() {

    }

    @Disabled
    @Test
    @DisplayName("User Sign Up | Fail : Duplicate userId")
    void signUp_Fail_Duplicate_userId() {
    }

    @Disabled
    @Test
    @DisplayName("User Sign Up | Fail : Duplicate email")
    void signUp_Fail_Duplicate_email() {
    }
}
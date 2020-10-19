package com.ewha.heydongdong.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("User Domain Test")
class UserTest {

    @Test
    @DisplayName("User Constructor Success")
    void testConstructor() {

        User newUser = User.builder()
                .userId("kim")
                .userName("김지우")
                .phone("010-0000-1111")
                .email("this@email.com")
                .noShowCount(0)
                .build();

        assertNotNull(newUser.getUserId());
    }
}
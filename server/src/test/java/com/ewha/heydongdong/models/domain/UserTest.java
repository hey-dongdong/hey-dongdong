package com.ewha.heydongdong.models.domain;

import com.ewha.heydongdong.model.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("User Domain Test")
class UserTest {

    @Test
    @DisplayName("User Constructor Success")
    void User_Constructor_Success() {

        User newUser = User.builder()
                .userId("kim")
                .userName("김지우")
                .phone("010-0000-1111")
                .email("this@email.com")
                .noShowCount(0)
                .build();

        assertNotNull(newUser.getUserId());
    }

    @Test
    @DisplayName("User Constructor Fail : UserId empty")
    void User_Constructor_Fail_UserId_empty() {

        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                    .userName("김지우")
                    .build();
        });
    }


}
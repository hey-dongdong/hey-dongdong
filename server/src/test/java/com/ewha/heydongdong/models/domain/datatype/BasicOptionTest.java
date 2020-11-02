package com.ewha.heydongdong.models.domain.datatype;

import com.ewha.heydongdong.module.model.domain.datatype.BasicOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("BasicOption Datatype Test")
class BasicOptionTest {

    @Test
    @DisplayName("BasicOption Constructor | Success")
    void BasicOption_Constructor_Success() {

        BasicOption basicOption = BasicOption.builder()
                .isHot(true)
                .isSmall(true)
                .isTumblr(true)
                .build();

        assertNotNull(basicOption);
    }

    @Test
    @DisplayName("BasicOption Constructor | Fail : isHot null")
    void BasicOption_Constructor_Fail_isHot_null() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BasicOption.builder()
                    .isSmall(true)
                    .isTumblr(true)
                    .build();
        });
    }

    @Test
    @DisplayName("BasicOption Constructor | Fail : isSmall null")
    void BasicOption_Constructor_Fail_isSmall_null() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BasicOption.builder()
                    .isHot(true)
                    .isTumblr(true)
                    .build();
        });
    }

    @Test
    @DisplayName("BasicOption Constructor | Fail : isTumblr null")
    void BasicOption_Constructor_Fail_isTumblr_null() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            BasicOption.builder()
                    .isHot(true)
                    .isSmall(true)
                    .build();
        });
    }
}
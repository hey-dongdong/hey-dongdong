package com.ewha.heydongdong.heydongdong.models.domain.datatype;

import com.ewha.heydongdong.model.domain.datatype.BasicOption;
import com.ewha.heydongdong.model.domain.datatype.CustomOption;
import com.ewha.heydongdong.model.domain.datatype.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Option Datatype Test")
class OptionTest {

    @Test
    @DisplayName("Option Constructor | Success")
    void Option_Constructor_Success() {
        // Given
        BasicOption basicOption = BasicOption.builder()
                .isSmall(true)
                .isHot(false)
                .isTumblr(true)
                .build();

        CustomOption customOption = CustomOption.builder()
                .chocolate(true)
                .soyMilk(true)
                .build();

        // When
        Option option = Option.builder()
                .basicOption(basicOption)
                .customOption(customOption)
                .build();

        // Then
        assertNotNull(option);
        System.out.println(option);
    }

    @Test
    @DisplayName("Option Constructor | Fail : BasicOption null")
    void Option_Constructor_Fail_BasicOption_null() {
        // Given
        CustomOption customOption = CustomOption.builder()
                .chocolate(true)
                .soyMilk(true)
                .build();

        // Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Option.builder()
                    .customOption(customOption)
                    .build();
        });
    }
}
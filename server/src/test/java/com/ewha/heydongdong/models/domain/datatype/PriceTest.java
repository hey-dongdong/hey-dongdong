package com.ewha.heydongdong.models.domain.datatype;

import com.ewha.heydongdong.module.model.domain.datatype.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Price Datatype Test")
class PriceTest {

    @Test
    @DisplayName("Price Constructor Success")
    void Price_Constructor_Success() {

        Price price = Price.builder()
                .smallHotPrice(1500)
                .largeHotPrice(2500)
                .build();

        assertNotNull(price);
    }
}
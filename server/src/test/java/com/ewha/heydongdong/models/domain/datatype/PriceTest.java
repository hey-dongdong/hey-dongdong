package com.ewha.heydongdong.models.domain.datatype;

import com.ewha.heydongdong.model.domain.datatype.Price;
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

    @Test
    @DisplayName("Price isHotAvailable Success")
    void Price_isHotAvailable_Success() {

        Price hotPrice = Price.builder()
                .smallHotPrice(1500)
                .largeHotPrice(2500)
                .build();

        Price icePrice = Price.builder()
                .smallIcePrice(2000)
                .largeIcePrice(3000)
                .build();

        assertTrue(hotPrice.isHotAvailable());
        assertFalse(icePrice.isHotAvailable());
    }

    @Test
    @DisplayName("Price isIceAvailable Success")
    void Price_isIceAvailable_Success() {

        Price hotPrice = Price.builder()
                .smallHotPrice(1500)
                .largeHotPrice(2500)
                .build();

        Price icePrice = Price.builder()
                .smallIcePrice(2000)
                .largeIcePrice(3000)
                .build();

        assertTrue(icePrice.isIceAvailable());
        assertFalse(hotPrice.isIceAvailable());
    }
}
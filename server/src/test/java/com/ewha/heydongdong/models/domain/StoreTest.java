package com.ewha.heydongdong.models.domain;

import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.model.domain.datatype.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Store Domain Test")
class StoreTest {

    @Test
    @DisplayName("Store Constructor Success")
    void Store_Constructor_Success() {

        Store store = Store.builder()
                .storeId(1)
                .storeName("학생문화관점")
                .location("sample location")
                .openTime("sample openTime")
                .tel("sample tel")
                .position(Position.builder()
                        .latitude(30.0)
                        .longitude(120.0)
                        .build())
                .build();

        assertNotNull(store);
    }

    @Test
    @DisplayName("Store Constructor Fail : StoreId null")
    void Store_Constructor_Fail_StoreId_null() {

        assertThrows(IllegalArgumentException.class, () -> {
            Store.builder()
                    .storeName("학생문화관점")
                    .location("sample location")
                    .openTime("sample openTime")
                    .tel("sample tel")
                    .position(Position.builder()
                            .latitude(30.0)
                            .longitude(120.0)
                            .build())
                    .build();

        });
    }
}
package com.ewha.heydongdong.service;

import com.ewha.heydongdong.domain.Store;
import com.ewha.heydongdong.repository.StoreRepository;
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
import static org.springframework.util.Assert.noNullElements;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Store Service Test")
@Transactional
class StoreServiceTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("Load all stores Success")
    void getAllStores() {
        List<Store> foundStores = storeRepository.findAll();
        assertNotNull(foundStores);
        noNullElements(foundStores, "Null store element exists");
        assertEquals(15, foundStores.size());
    }
}
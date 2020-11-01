package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.repository.StoreRepository;
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
    @DisplayName("Get all stores Success")
    void getAllStores() {
        List<Store> stores = storeRepository.findAll();
        assertNotNull(stores);
        noNullElements(stores, "Null store element exists");
        assertEquals(15, stores.size());
    }

    @Test
    @DisplayName("Get one store and menus Success")
    void getOneStoreAndMenus() {
        Store store = storeRepository.getOne(1);
        List<Menu> menus = store.getMenus();
        noNullElements(menus, "Null menu element exists");
        for (Menu menu : menus) System.out.println(menu);
    }
}
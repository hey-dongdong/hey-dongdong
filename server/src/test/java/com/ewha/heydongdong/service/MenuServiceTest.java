package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.Menu;
import com.ewha.heydongdong.repository.MenuRepository;
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
@DisplayName("Menu Service Test")
@Transactional
class MenuServiceTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("Get all menus Success")
    void getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        assertNotNull(menus);
        noNullElements(menus, "Null menu element exists");
        assertEquals(6, menus.size());

        for (Menu menu : menus)
            System.out.println(menu);
    }
}
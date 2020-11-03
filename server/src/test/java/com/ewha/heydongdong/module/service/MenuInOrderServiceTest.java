package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.MenuInOrder;
import com.ewha.heydongdong.module.model.domain.datatype.BasicOption;
import com.ewha.heydongdong.module.model.domain.datatype.CustomOption;
import com.ewha.heydongdong.module.model.domain.datatype.Option;
import com.ewha.heydongdong.module.repository.MenuInOrderRepository;
import com.ewha.heydongdong.module.repository.OrderRepository;
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
@DisplayName("MenuInOrder Service Test")
@Transactional
class MenuInOrderServiceTest {

    @Autowired
    private MenuInOrderRepository menuInOrderRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Get one menuInOrder | Success")
    void getOneMenuInOrder_Success() {
        MenuInOrder menu = menuInOrderRepository.getOne(1L);
        assertNotNull(menu);
    }

    @Test
    @DisplayName("Add one menuInOrder | Success")
    void addOneMenuInOrder() {
        // Given
        MenuInOrder menu = MenuInOrder.builder()
                .count(1)
                .menu(Menu.builder().menuId(5).build())
                .order(orderRepository.getOne(1L))
                .price(3000)
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isTumblr(true).isHot(false).isSmall(false).build())
                        .customOption(CustomOption.builder().soyMilk(true).shotAmericano(1).build())
                        .build())
                .build();

        // When
        MenuInOrder insertedMenu = menuInOrderRepository.save(menu);

        // Then
        assertNotNull(insertedMenu);
        assertEquals(menu, menuInOrderRepository.getOne(menu.getId()));
    }
}
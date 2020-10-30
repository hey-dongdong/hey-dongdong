package com.ewha.heydongdong.service;

import com.ewha.heydongdong.domain.Menu;
import com.ewha.heydongdong.domain.MenuInOrder;
import com.ewha.heydongdong.domain.datatype.BasicOption;
import com.ewha.heydongdong.domain.datatype.CustomOption;
import com.ewha.heydongdong.domain.datatype.Option;
import com.ewha.heydongdong.repository.MenuInOrderRepository;
import com.ewha.heydongdong.repository.OrderRepository;
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
    @DisplayName("Get all menusInOrders | Success")
    void getAllMenusInOrders_Success() {
        List<MenuInOrder> menus = menuInOrderRepository.findAll();
        assertNotNull(menus);
        noNullElements(menus, "Null menu element exists");
        System.out.println(menus);
    }

    @Test
    @DisplayName("Get one menuInOrder | Success")
    void getOneMenuInOrder_Success() {
        MenuInOrder menu = menuInOrderRepository.getOne(1L);
        assertNotNull(menu);
        System.out.println(menu);
    }

    @Test
    @DisplayName("Add one menuInOrder | Success")
    void addOneMenuInOrder() {
        // Given
        MenuInOrder menu = MenuInOrder.builder()
                .count(1)
                .menu(Menu.builder().menuId(5).build())
                .order(orderRepository.getOne(0L))
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
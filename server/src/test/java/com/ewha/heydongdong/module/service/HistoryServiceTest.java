package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.Order;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.OrderDto;
import com.ewha.heydongdong.module.model.dto.SimpleMenuDto;
import com.ewha.heydongdong.module.model.dto.SimpleStoreDto;
import com.ewha.heydongdong.module.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("History Service Test")
@Transactional
class HistoryServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all history of test user | Success")
    void getAllHistoryOfUser_Success() {
        // Given
        User user = User.builder()
                .userId("test_user")
                .build();

        // When
        List<Order> orders = orderRepository.findByUserAndProgress(user, Progress.DONE);

        // Then
        for (Order order : orders)
            assertEquals(order.getProgress(), Progress.DONE);
    }

    @Test
    @DisplayName("Build history of test user | Success")
    void buildHistoryOfUser_Success() {
        // Given
        User user = User.builder()
                .userId("test_user")
                .build();
        List<Order> orders = orderRepository.findByUserAndProgress(user, Progress.DONE);

        // When
        List<OrderDto> history = new ArrayList<>();
        for (Order order : orders) {
            Menu firstMenu = order.getMenus().get(0).getMenu();
            history.add(OrderDto.builder()
                    .orderId(order.getOrderId())
                    .orderAt(order.getOrderAt())
                    .totalCount(order.getTotalCount())
                    .totalPrice(order.getTotalPrice())
                    .menu(SimpleMenuDto.builder()
                            .menuId(firstMenu.getMenuId())
                            .menuName(firstMenu.getMenuName())
                            .imgUrl(firstMenu.getImgUrl())
                            .build())
                    .store(SimpleStoreDto.builder()
                            .storeId(order.getStore().getStoreId())
                            .storeName(order.getStore().getStoreName())
                            .build())
                    .build());
        }

        // Then
        for (OrderDto orderDto : history) {
            assertNotNull(orderDto.getMenu());
        }
    }
}
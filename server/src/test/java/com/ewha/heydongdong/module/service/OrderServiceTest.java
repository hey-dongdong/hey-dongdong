package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.model.domain.Order;
import com.ewha.heydongdong.module.model.domain.User;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.util.Assert.noNullElements;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Order Service Test")
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Get all orders | Success")
    void getAllOrders_Success() {
        // When
        List<Order> orders = orderRepository.findAll();

        // Then
        assertNotEquals(0, orders.size());
        noNullElements(orders, "Null order element exists");
    }

    @Test
    @DisplayName("Get all orders of test user | Success : User found")
    void getAllOrdersOfOneUser_Success() {
        // Given
        User user = User.builder()
                .userId("test_user")
                .build();

        // When
        List<Order> orders = orderRepository.findByUser(user);

        // Then
        assertNotEquals(0, orders.size());
        noNullElements(orders, "Null order element exists");
    }

    @Test
    @DisplayName("Get all orders of test user | Fail : No such user")
    void getAllOrdersOfOneUser_Fail_NoUserFound() {
        // Given
        User user = User.builder()
                .userId("not_existing_user")
                .build();

        // When
        List<Order> orders = orderRepository.findByUser(user);

        // Then
        assertEquals(0, orders.size());
    }
}
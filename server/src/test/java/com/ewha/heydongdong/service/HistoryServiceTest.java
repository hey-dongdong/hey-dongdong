package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.Menu;
import com.ewha.heydongdong.model.domain.Order;
import com.ewha.heydongdong.model.domain.User;
import com.ewha.heydongdong.model.domain.datatype.Progress;
import com.ewha.heydongdong.model.dto.MenuInHistoryDto;
import com.ewha.heydongdong.model.dto.StoreInHistoryDto;
import com.ewha.heydongdong.model.dto.UserHistoryDto;
import com.ewha.heydongdong.model.protocol.Header;
import com.ewha.heydongdong.model.protocol.Response;
import com.ewha.heydongdong.repository.OrderRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.sql.Timestamp;
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
        List<UserHistoryDto> history = new ArrayList<>();
        for (Order order : orders) {
            Menu firstMenu = order.getMenus().get(0).getMenu();
            history.add(UserHistoryDto.builder()
                    .orderId(order.getOrderId())
                    .orderAt(order.getOrderAt())
                    .totalCount(order.getTotalCount())
                    .totalPrice(order.getTotalPrice())
                    .menu(MenuInHistoryDto.builder()
                            .menuId(firstMenu.getMenuId())
                            .menuName(firstMenu.getMenuName())
                            .menuThumbnail(firstMenu.getImgUrl())
                            .build())
                    .store(StoreInHistoryDto.builder()
                            .storeId(order.getStore().getStoreId())
                            .storeName(order.getStore().getStoreName())
                            .build())
                    .build());
        }

        // Then
        for (UserHistoryDto userHistoryDto : history) {
            assertNotNull(userHistoryDto.getMenu());
        }
    }

    @Test
    @DisplayName("Build json from history | Success")
    void buildJsonFromHistory_Success() {
        // Given
        List<UserHistoryDto> history = new ArrayList<>();
        history.add(UserHistoryDto.builder()
                .orderId(1L)
                .orderAt(Timestamp.valueOf("2020-10-29 2:55:33"))
                .totalCount(2)
                .totalPrice(4800)
                .menu(MenuInHistoryDto.builder()
                        .menuId(4)
                        .menuName("카푸치노")
                        .menuThumbnail("urlurl.com")
                        .build())
                .store(StoreInHistoryDto.builder()
                        .storeId(3)
                        .storeName("학생문화관점")
                        .build())
                .build());

        // When
        Header header = new Header("GetHistoryResponse", "test_user");
        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orders", objectMapper.valueToTree(history));
        Response response = new Response(header, payload);

        // Then
        assertNotNull(response);
        System.out.println(objectMapper.valueToTree(response).toPrettyString());
    }
}
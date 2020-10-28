package com.ewha.heydongdong.service;

import com.ewha.heydongdong.domain.Menu;
import com.ewha.heydongdong.domain.Order;
import com.ewha.heydongdong.domain.User;
import com.ewha.heydongdong.domain.datatype.Progress;
import com.ewha.heydongdong.dto.MenuInHistoryDto;
import com.ewha.heydongdong.dto.StoreInHistoryDto;
import com.ewha.heydongdong.dto.UserHistoryDto;
import com.ewha.heydongdong.protocol.Header;
import com.ewha.heydongdong.protocol.Response;
import com.ewha.heydongdong.repository.OrderRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private OrderRepository orderRepository;

    public String getUserHistory(String userId) {

        List<Order> orders = orderRepository.findByUserAndProgress(User.builder().userId(userId).build(), Progress.DONE);
        List<UserHistoryDto> userHistoryDto = buildHistoryFromOrders(orders);
        String history = buildUserHistoryJson(userId, userHistoryDto);
        return history;
    }

    private List<UserHistoryDto> buildHistoryFromOrders(List<Order> orders) {

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
        return history;
    }

    private String buildUserHistoryJson(String userId, List<UserHistoryDto> history) {

        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Header header = new Header("GetHistoryResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orders", objectMapper.valueToTree(history));

        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }
}

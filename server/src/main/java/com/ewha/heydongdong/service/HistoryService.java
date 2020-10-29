package com.ewha.heydongdong.service;

import com.ewha.heydongdong.domain.Menu;
import com.ewha.heydongdong.domain.MenuInOrder;
import com.ewha.heydongdong.domain.Order;
import com.ewha.heydongdong.domain.User;
import com.ewha.heydongdong.domain.datatype.Progress;
import com.ewha.heydongdong.dto.*;
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
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private OrderRepository orderRepository;

    public String getUserHistory(String userId) {

        List<Order> orders = orderRepository.findByUserAndProgress(User.builder().userId(userId).build(), Progress.DONE);
        List<UserHistoryDto> userHistoryDto = buildUserHistoryFromOrders(orders);
        return buildUserHistoryJson(userId, userHistoryDto);
    }

    private List<UserHistoryDto> buildUserHistoryFromOrders(List<Order> orders) {

        // TODO [지우] 히스토리 0개일 경우 예외 처리 (프론트에서? 아니면 코드로?)
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

        Header header = new Header("GetUserHistoryResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orders", objectMapper.valueToTree(history));

        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }

    public String getUserHistoryDetail(String userId, Long orderId) {

        Optional<Order> orders = orderRepository.findById(orderId);
        if (orders.isEmpty()) {
            // TODO [지우] 예외처리
            return null;
        } else {
            UserHistoryDetailDto userHistoryDetailDto = buildUserHistoryDetailFromOrder(orders.get());
            return buildUserHistoryDetailJson(userId, userHistoryDetailDto);
        }
    }

    private UserHistoryDetailDto buildUserHistoryDetailFromOrder(Order order) {

        UserHistoryDto orderInfo = UserHistoryDto.builder()
                .orderId(order.getOrderId())
                .store(StoreInHistoryDto.builder()
                        .storeId(order.getStore().getStoreId())
                        .storeName(order.getStore().getStoreName())
                        .build())
                .orderAt(order.getOrderAt())
                .totalPrice(order.getTotalPrice())
                .build();

        List<MenuInHistoryDetailDto> menus = new ArrayList<>();
        for (MenuInOrder menu : order.getMenus()) {
            menus.add(MenuInHistoryDetailDto.builder()
                    .menuInOrderId(menu.getId())
                    .menu(MenuInHistoryDto.builder()
                            .menuId(menu.getMenu().getMenuId())
                            .menuName(menu.getMenu().getMenuName())
                            .build())
                    .option(menu.getOption())
                    .price(menu.getPrice())
                    .count(menu.getCount())
                    .build());
        }

        return UserHistoryDetailDto.builder()
                .orderInfo(orderInfo)
                .menus(menus)
                .build();
    }

    private String buildUserHistoryDetailJson(String userId, UserHistoryDetailDto historyDetail) {

        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Header header = new Header("GetUserHistoryDetailResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orderInfo", objectMapper.valueToTree(historyDetail.getOrderInfo()));
        payload.set("menus", objectMapper.valueToTree(historyDetail.getMenus()));

        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }
}

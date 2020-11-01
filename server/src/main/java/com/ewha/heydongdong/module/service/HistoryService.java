package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.MenuInOrder;
import com.ewha.heydongdong.module.model.domain.Order;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.*;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.protocol.Header;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.module.repository.OrderRepository;
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

    @Autowired
    private ObjectMapper objectMapper;

    public String getUserHistory(String userId) {
        List<Order> orders = orderRepository.findByUserAndProgress(User.builder().userId(userId).build(), Progress.DONE);
        checkIfHistoryExists(orders, userId);
        List<UserHistoryDto> userHistoryDto = buildUserHistoryFromOrders(orders);
        return buildUserHistoryJson(userId, userHistoryDto);
    }

    private void checkIfHistoryExists(List<Order> orders, String userId) {
        if (orders.isEmpty())
            throw new NoResultFromDBException("No history for userId=" + userId);
    }

    private List<UserHistoryDto> buildUserHistoryFromOrders(List<Order> orders) {
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
        Header header = new Header("GetUserHistoryResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orders", objectMapper.valueToTree(history));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }

    public String getUserHistoryDetail(String userId, Long orderId) {
        Optional<Order> orders = orderRepository.findById(orderId);
        checkIfOrderExists(orders, orderId);
        UserHistoryDetailDto userHistoryDetailDto = buildUserHistoryDetailFromOrder(orders.get());
        return buildUserHistoryDetailJson(userId, userHistoryDetailDto);
    }

    private void checkIfOrderExists(Optional<Order> orders, Long orderId) {
        if (orders.isEmpty())
            throw new InvalidRequestParameterException("orderId=" + orderId);
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
        Header header = new Header("GetUserHistoryDetailResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orderInfo", objectMapper.valueToTree(historyDetail.getOrderInfo()));
        payload.set("menus", objectMapper.valueToTree(historyDetail.getMenus()));

        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }
}
package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.*;
import com.ewha.heydongdong.module.model.dto.MenuInNewOrderDto;
import com.ewha.heydongdong.module.model.dto.NewOrderDto;
import com.ewha.heydongdong.module.model.dto.NewOrderInfoDto;
import com.ewha.heydongdong.module.repository.MenuInOrderRepository;
import com.ewha.heydongdong.module.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuInOrderRepository menuInOrderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String addNewOrder(JsonNode payload) throws JsonProcessingException {
        NewOrderDto newOrderDto = objectMapper.treeToValue(payload, NewOrderDto.class);
        Order order = orderRepository.save(buildOrderFromNewOrderDto(newOrderDto.getNewOrderInfo()));
        saveMenusInNewOrder(order, newOrderDto.getMenus());
        return buildNewOrderJsonResponse(order.getOrderId(), order.getUser().getUserId());
    }

    private Order buildOrderFromNewOrderDto(NewOrderInfoDto newOrderInfo) {
        return Order.builder()
                .orderAt(newOrderInfo.getOrderAt())
                .isNoShow(newOrderInfo.getIsNoShow())
                .progress(newOrderInfo.getProgress())
                .totalCount(newOrderInfo.getTotalCount())
                .totalPrice(newOrderInfo.getTotalPrice())
                .store(Store.builder()
                        .storeId(newOrderInfo.getStoreId())
                        .build())
                .user(User.builder()
                        .userId(newOrderInfo.getUserId())
                        .build())
                .build();
    }

    private void saveMenusInNewOrder(Order order, List<MenuInNewOrderDto> menus) {
        for (MenuInNewOrderDto menuInNewOrder : menus) {
            menuInOrderRepository.save(MenuInOrder.builder()
                    .menu(Menu.builder()
                            .menuId(menuInNewOrder.getMenuId())
                            .build())
                    .order(order)
                    .option(menuInNewOrder.getOption())
                    .price(menuInNewOrder.getPrice())
                    .count(menuInNewOrder.getCount())
                    .build());
        }
    }

    private String buildNewOrderJsonResponse(Long orderId, String userId) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("orderId", orderId);

        return objectMapper.valueToTree(Response.builder()
                .header(ResponseHeader.builder()
                        .name("GetStoreHistoryResponse")
                        .message(userId).build())
                .payload(payload)
                .build()).toPrettyString();
    }
}

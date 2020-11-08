package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.*;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
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

    @Autowired
    private JsonBuilder jsonBuilder;

    public String addNewOrder(JsonNode payload) throws JsonProcessingException {
        NewOrderDto newOrderDto = objectMapper.treeToValue(payload, NewOrderDto.class);
        Order order = orderRepository.save(buildOrderFromNewOrderDto(newOrderDto.getNewOrderInfo()));
        saveMenusInNewOrder(order, newOrderDto.getMenus());
        return buildNewOrderJsonResponse(order.getOrderId(), order.getUser(), newOrderDto);
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
                            .menuName(menuInNewOrder.getMenuName())
                            .build())
                    .order(order)
                    .option(menuInNewOrder.getOption())
                    .price(menuInNewOrder.getPrice())
                    .count(menuInNewOrder.getCount())
                    .build());
        }
    }

    private String buildNewOrderJsonResponse(Long orderId, User user, NewOrderDto newOrderDto) {

        ResponseHeader header = jsonBuilder.buildResponseHeader("AddNewOrderResponse", user.getUserId());
        ObjectNode payload = jsonBuilder.buildResponsePayload(
                new String[]{"orderId", "userName", "orderAt", "totalPrice"},
                new String[]{String.valueOf(orderId), user.getUserName(),
                        String.valueOf(newOrderDto.getNewOrderInfo().getOrderAt()), String.valueOf(newOrderDto.getNewOrderInfo().getTotalPrice())});
        payload.set("menus", objectMapper.valueToTree(newOrderDto.getMenus()));
        return jsonBuilder.buildJsonWithHeaderAndPayload(header, payload);
    }

    public String updateOrderProgress(JsonNode payload) {
        Order order = findOrderById(payload.get("orderId").asLong());
        order.setProgress(Progress.valueOf(payload.get("progress").asText()));
        Order updatedOrder = orderRepository.save(order);
        return buildUpdateOrderProgressJsonResponse(updatedOrder.getOrderId());
    }

    private Order findOrderById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidRequestParameterException("orderId=" + orderId));
    }

    private String buildUpdateOrderProgressJsonResponse(Long orderId) {
        return objectMapper.valueToTree(Response.builder()
                .header(ResponseHeader.builder()
                        .name("UpdateOrderProgressResponse")
                        .message(String.valueOf(orderId))
                        .build())
                .build()).toPrettyString();
    }
}

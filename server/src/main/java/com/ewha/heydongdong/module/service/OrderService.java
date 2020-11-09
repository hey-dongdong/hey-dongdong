package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.module.model.domain.*;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.MenuInOrderDto;
import com.ewha.heydongdong.module.model.dto.OrderDetailDto;
import com.ewha.heydongdong.module.model.dto.OrderDto;
import com.ewha.heydongdong.module.repository.MenuInOrderRepository;
import com.ewha.heydongdong.module.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        OrderDetailDto newOrderDetailDto = objectMapper.treeToValue(payload, OrderDetailDto.class);
        Order newOrder = saveNewOrderAndMenus(newOrderDetailDto);
        newOrderDetailDto.getOrderInfo().setOrderId(newOrder.getOrderId());
        return buildNewOrderJsonResponse(newOrderDetailDto);
    }

    private Order saveNewOrderAndMenus(OrderDetailDto newOrderDetailDto) {
        Order order = saveNewOrder(newOrderDetailDto.getOrderInfo());
        saveMenusInNewOrder(newOrderDetailDto.getMenus(), order);
        return order;
    }

    private Order saveNewOrder(OrderDto orderDto) {
        Order newOrder = buildOrderFromOrderDto(orderDto);
        return orderRepository.save(newOrder);
    }

    private Order buildOrderFromOrderDto(OrderDto newOrderInfo) {
        return Order.builder()
                .orderAt(newOrderInfo.getOrderAt())
                .isNoShow(newOrderInfo.isNoShow())
                .progress(newOrderInfo.getProgress())
                .totalCount(newOrderInfo.getTotalCount())
                .totalPrice(newOrderInfo.getTotalPrice())
                .store(Store.builder()
                        .storeId(newOrderInfo.getStore().getStoreId())
                        .build())
                .user(User.builder()
                        .userId(newOrderInfo.getUser().getUserId())
                        .build())
                .build();
    }

    private void saveMenusInNewOrder(List<MenuInOrderDto> menus, Order newOrder) {
        for (MenuInOrderDto menu : menus) {
            menuInOrderRepository.save(MenuInOrder.builder()
                    .menu(Menu.builder().menuId(menu.getMenu().getMenuId()).build())
                    .order(newOrder)
                    .option(menu.getOption())
                    .price(menu.getPrice())
                    .count(menu.getCount())
                    .build());
        }
    }

    private String buildNewOrderJsonResponse(OrderDetailDto newOrderDetailDto) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("AddNewOrderResponse", newOrderDetailDto.getOrderInfo().getUser().getUserId()),
                jsonBuilder.buildResponsePayloadFromObject(new String[]{"orderInfo", "menus"},
                        new Object[]{newOrderDetailDto.getOrderInfo(), newOrderDetailDto.getMenus()})
        );
    }


    public String updateOrderProgress(JsonNode payload) {
        Order order = findRequiredOrderById(payload.get("orderId").asLong());
        order.setProgress(Progress.valueOf(payload.get("progress").asText()));
        orderRepository.save(order);
        return jsonBuilder.buildJsonWithHeader("UpdateOrderProgressResponse", String.valueOf(order.getOrderId()));
    }

    private Order findRequiredOrderById(long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidRequestParameterException("orderId=" + orderId));
    }
}

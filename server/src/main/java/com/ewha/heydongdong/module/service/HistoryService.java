package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.*;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.*;
import com.ewha.heydongdong.module.repository.MyMenuRepository;
import com.ewha.heydongdong.module.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistoryService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MyMenuRepository myMenuRepository;

    public String getUserHistory(String userId) {
        List<Order> orders = orderRepository.findByUserAndProgress(User.builder().userId(userId).build(), Progress.DONE);
        checkIfUserHistoryExists(orders, userId);
        List<UserHistoryDto> userHistoryDto = buildUserHistoryFromOrders(orders);
        return buildUserHistoryJsonResponse(userId, userHistoryDto);
    }

    private void checkIfUserHistoryExists(List<Order> orders, String userId) {
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

    private String buildUserHistoryJsonResponse(String userId, List<UserHistoryDto> history) {
        ResponseHeader header = new ResponseHeader("GetUserHistoryResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orders", objectMapper.valueToTree(history));

        return objectMapper.valueToTree(Response.builder().header(header).payload(payload).build()).toPrettyString();
    }

    public String getUserHistoryDetail(String userId, Long orderId) {
        Optional<Order> orders = orderRepository.findById(orderId);
        checkIfOrderExists(orders, orderId);
        UserHistoryDetailDto userHistoryDetailDto = buildUserHistoryDetailFromOrder(orders.get());
        return buildUserHistoryDetailJsonResponse(userId, userHistoryDetailDto);
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

        return UserHistoryDetailDto.builder()
                .orderInfo(orderInfo)
                .menus(buildMenusInHistoryFromOrder(order))
                .build();
    }

    private List<MenuInHistoryDetailDto> buildMenusInHistoryFromOrder(Order order) {
        List<MenuInHistoryDetailDto> menus = new ArrayList<>();
        for (MenuInOrder menuInOrder : order.getMenus()) {
            Optional<MyMenu> myMenu = myMenuRepository.findByUserAndMenuInOrder(
                    User.builder().userId(order.getUser().getUserId()).build(),
                    MenuInOrder.builder().id(menuInOrder.getId()).build());
            if (myMenu.isPresent())
                menus.add(MenuInHistoryDetailDto.builder()
                        .menuInOrderId(menuInOrder.getId())
                        .menu(MenuInHistoryDto.builder()
                                .menuId(menuInOrder.getMenu().getMenuId())
                                .menuName(menuInOrder.getMenu().getMenuName())
                                .menuThumbnail(menuInOrder.getMenu().getImgUrl())
                                .build())
                        .option(menuInOrder.getOption())
                        .price(menuInOrder.getPrice())
                        .count(menuInOrder.getCount())
                        .menuLiked(true)
                        .myMenuId(myMenu.get().getMyMenuId())
                        .build());
            else
                menus.add(MenuInHistoryDetailDto.builder()
                        .menuInOrderId(menuInOrder.getId())
                        .menu(MenuInHistoryDto.builder()
                                .menuId(menuInOrder.getMenu().getMenuId())
                                .menuName(menuInOrder.getMenu().getMenuName())
                                .menuThumbnail(menuInOrder.getMenu().getImgUrl())
                                .build())
                        .option(menuInOrder.getOption())
                        .price(menuInOrder.getPrice())
                        .count(menuInOrder.getCount())
                        .menuLiked(false)
                        .build());
        }
        return menus;
    }

    private String buildUserHistoryDetailJsonResponse(String userId, UserHistoryDetailDto historyDetail) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("orderInfo", objectMapper.valueToTree(historyDetail.getOrderInfo()));
        payload.set("menus", objectMapper.valueToTree(historyDetail.getMenus()));

        return objectMapper.valueToTree(Response.builder()
                .header(ResponseHeader.builder()
                        .name("GetUserHistoryDetailResponse")
                        .message(String.valueOf(userId)).build())
                .payload(payload)
                .build()).toPrettyString();
    }

    public String getStoreHistory(Integer storeId) {
        List<Order> doneOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.DONE);
        List<Order> noShowOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.NOSHOW);
        checkIfStoreOrdersExist(doneOrders, noShowOrders, storeId);
        return buildStoreOrdersJsonResponse(storeId, buildStoreHistoryFromOrders(doneOrders), buildStoreHistoryFromOrders(noShowOrders));
    }

    private void checkIfStoreOrdersExist(List<Order> doneOrders, List<Order> noShowOrders, Integer storeId) {
        if (doneOrders.isEmpty() && noShowOrders.isEmpty())
            throw new NoResultFromDBException("No history for storeId=" + storeId);
    }

    private List<StoreHistoryDetailDto> buildStoreHistoryFromOrders(List<Order> orders) {
        List<StoreHistoryDetailDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            ordersDto.add(StoreHistoryDetailDto.builder()
                    .orderInfo(StoreHistoryDto.builder()
                            .orderId(order.getOrderId())
                            .orderAt(order.getOrderAt())
                            .user(UserInStoreHistoryDto.builder()
                                    .userId(order.getUser().getUserId())
                                    .userName(order.getUser().getUserName())
                                    .phone(order.getUser().getPhone())
                                    .build())
                            .build())
                    .menus(buildMenusInHistoryFromOrder(order))
                    .build());
        }
        return ordersDto;
    }

    private String buildStoreOrdersJsonResponse(Integer storeId, List<StoreHistoryDetailDto> doneOrdersDto, List<StoreHistoryDetailDto> noShowOrdersDto) {
        Map<String, List<StoreHistoryDetailDto>> storeHistory = new HashMap<>();
        storeHistory.put("doneOrders", doneOrdersDto);
        storeHistory.put("noShowOrders", noShowOrdersDto);

        return objectMapper.valueToTree(Response.builder()
                .header(ResponseHeader.builder()
                        .name("GetStoreHistoryResponse")
                        .message(String.valueOf(storeId)).build())
                .payload(objectMapper.valueToTree(storeHistory))
                .build()).toPrettyString();
    }

    public String getStoreOrders(Integer storeId) {
        List<Order> waitingOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.WAITING);
        List<Order> makingOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.MAKING);
        List<Order> readyOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.READY);
        checkIfStoreOrdersExist(waitingOrders, makingOrders, readyOrders, storeId);
        return buildStoreOrdersJsonResponse(storeId, buildStoreHistoryFromOrders(waitingOrders), buildStoreHistoryFromOrders(makingOrders), buildStoreHistoryFromOrders(readyOrders));
    }

    private void checkIfStoreOrdersExist(List<Order> waitingOrders, List<Order> makingOrders, List<Order> readyOrders, Integer storeId) {
        if (waitingOrders.isEmpty() && makingOrders.isEmpty() && readyOrders.isEmpty())
            throw new NoResultFromDBException("No orders for storeId=" + storeId);
    }

    private String buildStoreOrdersJsonResponse(Integer storeId, List<StoreHistoryDetailDto> waitingOrdersDto, List<StoreHistoryDetailDto> makingOrdersDto, List<StoreHistoryDetailDto> readyOrdersDto) {
        Map<String, List<StoreHistoryDetailDto>> storeHistory = new HashMap<>();
        storeHistory.put("waitingOrders", waitingOrdersDto);
        storeHistory.put("makingOrders", makingOrdersDto);
        storeHistory.put("readyOrders", readyOrdersDto);

        return objectMapper.valueToTree(Response.builder()
                .header(ResponseHeader.builder()
                        .name("GetStoreOrdersResponse")
                        .message(String.valueOf(storeId)).build())
                .payload(objectMapper.valueToTree(storeHistory))
                .build()).toPrettyString();
    }
}
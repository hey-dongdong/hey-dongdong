package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.module.model.domain.*;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.*;
import com.ewha.heydongdong.module.repository.MyMenuRepository;
import com.ewha.heydongdong.module.repository.OrderRepository;
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
    private MyMenuRepository myMenuRepository;

    @Autowired
    private JsonBuilder jsonBuilder;


    public String getUserHistory(String userId) {
        List<Order> orders = findUserHistory(userId);
        List<OrderDto> orderDto = buildUserHistoryFromOrders(orders);
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetMyMenusResponse", userId),
                jsonBuilder.buildResponsePayloadFromObject("menus", orderDto)
        );
    }

    private List<Order> findUserHistory(String userId) {
        List<Order> orders = orderRepository.findByUserAndProgress(User.builder().userId(userId).build(), Progress.DONE);
        checkIfUserHistoryExists(orders, userId);
        return orders;
    }

    private void checkIfUserHistoryExists(List<Order> orders, String userId) {
        if (orders.isEmpty())
            throw new NoResultFromDBException("No history for userId=" + userId);
    }

    private List<OrderDto> buildUserHistoryFromOrders(List<Order> orders) {
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
        return history;
    }


    public String getUserHistoryDetail(String userId, Long orderId) {
        Order order = findRequiredOrderById(orderId);
        HistoryDetailDto historyDetailDto = buildUserHistoryDetailFromOrder(order);
        return buildUserHistoryDetailJsonResponse(userId, historyDetailDto);
    }

    private Order findRequiredOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidRequestParameterException("orderId=" + orderId));
    }

    private HistoryDetailDto buildUserHistoryDetailFromOrder(Order order) {
        OrderDto orderInfo = buildOrderDtoFromOrder(order);
        List<MenuInOrderDto> menus = buildMenuInOrderDtoFromOrder(order);
        return HistoryDetailDto.builder()
                .orderInfo(orderInfo)
                .menus(menus)
                .build();
    }

    private OrderDto buildOrderDtoFromOrder(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .store(SimpleStoreDto.builder()
                        .storeId(order.getStore().getStoreId())
                        .storeName(order.getStore().getStoreName())
                        .build())
                .orderAt(order.getOrderAt())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    private List<MenuInOrderDto> buildMenuInOrderDtoFromOrder(Order order) {
        List<MenuInOrderDto> menus = new ArrayList<>();
        for (MenuInOrder menuInOrder : order.getMenus())
            addMenuInOrderToMenus(order, menuInOrder, menus);
        return menus;
    }

    private void addMenuInOrderToMenus(Order order, MenuInOrder menuInOrder, List<MenuInOrderDto> menus) {
        Optional<MyMenu> myMenu = myMenuRepository.findByUserAndMenuInOrder(User.builder().userId(order.getUser().getUserId()).build(),
                MenuInOrder.builder().id(menuInOrder.getId()).build());
        if (myMenu.isPresent())
            addMenuInOrderWithMyMenuId(menuInOrder, menus, myMenu.get().getMyMenuId());
        else
            addMenuInOrderWithoutMyMenuId(menuInOrder, menus);
    }

    private void addMenuInOrderWithMyMenuId(MenuInOrder menuInOrder, List<MenuInOrderDto> menus, Long myMenuId) {
        menus.add(MenuInOrderDto.builder()
                .menuInOrderId(menuInOrder.getId())
                .menu(SimpleMenuDto.builder()
                        .menuId(menuInOrder.getMenu().getMenuId())
                        .menuName(menuInOrder.getMenu().getMenuName())
                        .build())
                .option(menuInOrder.getOption())
                .price(menuInOrder.getPrice())
                .count(menuInOrder.getCount())
                .menuLiked(true)
                .myMenuId(myMenuId)
                .build());
    }

    private void addMenuInOrderWithoutMyMenuId(MenuInOrder menuInOrder, List<MenuInOrderDto> menus) {
        menus.add(MenuInOrderDto.builder()
                .menuInOrderId(menuInOrder.getId())
                .menu(SimpleMenuDto.builder()
                        .menuId(menuInOrder.getMenu().getMenuId())
                        .menuName(menuInOrder.getMenu().getMenuName())
                        .build())
                .option(menuInOrder.getOption())
                .price(menuInOrder.getPrice())
                .count(menuInOrder.getCount())
                .menuLiked(false)
                .build());
    }

    private String buildUserHistoryDetailJsonResponse(String userId, HistoryDetailDto historyDetailDto) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetUserHistoryDetailResponse", userId),
                jsonBuilder.buildResponsePayloadFromObject(new String[]{"orderInfo", "menus"},
                        new Object[]{historyDetailDto.getOrderInfo(), historyDetailDto.getMenus()})
        );
    }


    public String getStoreHistory(Integer storeId) {
        List<Order> doneOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.DONE);
        List<Order> noShowOrders = orderRepository.findByStoreAndProgress(Store.builder().storeId(storeId).build(), Progress.NOSHOW);
        checkIfStoreHistoryExists(doneOrders, noShowOrders, storeId);
        List<HistoryDetailDto> doneOrdersDto = buildStoreHistoryFromOrders(doneOrders);
        List<HistoryDetailDto> noShowOrdersDto = buildStoreHistoryFromOrders(noShowOrders);
        return buildStoreHistoryJsonResponse(storeId, doneOrdersDto, noShowOrdersDto);
    }

    private void checkIfStoreHistoryExists(List<Order> doneOrders, List<Order> noShowOrders, Integer storeId) {
        if (doneOrders.isEmpty() && noShowOrders.isEmpty())
            throw new NoResultFromDBException("No history for storeId=" + storeId);
    }

    private List<HistoryDetailDto> buildStoreHistoryFromOrders(List<Order> orders) {
        List<HistoryDetailDto> historyDto = new ArrayList<>();
        for (Order order : orders) {
            historyDto.add(HistoryDetailDto.builder()
                    .orderInfo(OrderDto.builder()
                            .orderId(order.getOrderId())
                            .orderAt(order.getOrderAt())
                            .user(SimpleUserDto.builder()
                                    .userId(order.getUser().getUserId())
                                    .userName(order.getUser().getUserName())
                                    .phone(order.getUser().getPhone())
                                    .build())
                            .build())
                    .menus(buildMenuInOrderDtoFromOrder(order))
                    .build());
        }
        return historyDto;
    }

    private String buildStoreHistoryJsonResponse(Integer storeId, List<HistoryDetailDto> doneOrdersDto, List<HistoryDetailDto> noShowOrdersDto) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetStoreHistoryResponse", String.valueOf(storeId)),
                jsonBuilder.buildResponsePayloadFromObject(new String[]{"doneOrders", "noShowOrders"},
                        new Object[]{doneOrdersDto, noShowOrdersDto})
        );
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

    private String buildStoreOrdersJsonResponse(Integer storeId, List<HistoryDetailDto> waitingOrdersDto, List<HistoryDetailDto> makingOrdersDto, List<HistoryDetailDto> readyOrdersDto) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetStoreOrdersResponse", String.valueOf(storeId)),
                jsonBuilder.buildResponsePayloadFromObject(new String[]{"waitingOrders", "makingOrders", "readyOrders"},
                        new Object[]{waitingOrdersDto, makingOrdersDto, readyOrdersDto})
        );
    }
}
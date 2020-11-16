package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.datatype.BasicOption;
import com.ewha.heydongdong.module.model.domain.datatype.CustomOption;
import com.ewha.heydongdong.module.model.domain.datatype.Option;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Add new order | Success")
    void addNewOrder_Success() throws Exception {

        OrderDto newOrderInfo = OrderDto.builder()
                .orderAt(Timestamp.valueOf("2020-11-06 16:16:16"))
                .isNoShow(false)
                .progress(Progress.WAITING)
                .store(SimpleStoreDto.builder().storeId(1).build())
                .user(SimpleUserDto.builder().userId("test_user").build())
                .totalCount(3)
                .totalPrice(8100)
                .build();
        List<MenuInOrderDto> menus = new ArrayList<>();
        menus.add(MenuInOrderDto.builder()
                .menu(SimpleMenuDto.builder().menuId(23).build())
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isHot(true).isSmall(true).isTumblr(false).build())
                        .build())
                .price(1500)
                .count(1)
                .build());
        menus.add(MenuInOrderDto.builder()
                .menu(SimpleMenuDto.builder().menuId(32).build())
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isHot(true).isSmall(true).isTumblr(false).build())
                        .customOption(CustomOption.builder().shotAmericano(1).build())
                        .build())
                .price(2000)
                .count(1)
                .build());
        menus.add(MenuInOrderDto.builder()
                .menu(SimpleMenuDto.builder().menuId(21).build())
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isHot(false).isSmall(false).isTumblr(true).build())
                        .customOption(CustomOption.builder().shotAmericano(1).build())
                        .build())
                .price(2300)
                .count(2)
                .build());
        OrderDetailDto newOrder = OrderDetailDto.builder().orderInfo(newOrderInfo).menus(menus).build();

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("AddNewOrderRequest", "test_user"), objectMapper.valueToTree(newOrder)));

        mockMvc.perform(post("/order/add")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    @DisplayName("Get order progress | Success")
    void getOrderProgress_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("orderId", 1);

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetOrderProgressRequest", "test_user"), objectMapper.valueToTree(payload)));

        payload.removeAll();
        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("GetOrderProgressResponse")
                        .message("1")
                        .build())
                .payload(payload.put("progress", "DONE"))
                .build();

        mockMvc.perform(post("/order/get-progress")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Get order progress | Fail : Invalid orderId")
    void getOrderProgress_Fail_InvalidOrderId() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("orderId", 50000);

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetOrderProgressRequest", "test_user"), objectMapper.valueToTree(payload)));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [orderId=50000]")
                        .build())
                .build();

        mockMvc.perform(post("/order/get-progress")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }



    @Test
    @DisplayName("Update order progress | Success")
    void updateOrderProgress_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("orderId", 1);
        payload.put("progress", "MAKING");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("UpdateOrderProgressRequest", "admin_eng"), objectMapper.valueToTree(payload)));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("UpdateOrderProgressResponse")
                        .message("1")
                        .build())
                .build();

        mockMvc.perform(post("/order/update-progress")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }

    @Test
    @DisplayName("Update order progress | Fail : Invalid orderId")
    void updateOrderProgress_Fail_InvalidOrderId() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("orderId", 50000);
        payload.put("progress", "MAKING");

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("UpdateOrderProgressRequest", "admin_eng"), objectMapper.valueToTree(payload)));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [orderId=50000]")
                        .build())
                .build();

        mockMvc.perform(post("/order/update-progress")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }
}
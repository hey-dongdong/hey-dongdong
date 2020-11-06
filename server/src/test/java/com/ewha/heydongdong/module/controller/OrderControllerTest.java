package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.module.model.domain.datatype.BasicOption;
import com.ewha.heydongdong.module.model.domain.datatype.CustomOption;
import com.ewha.heydongdong.module.model.domain.datatype.Option;
import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.ewha.heydongdong.module.model.dto.MenuInNewOrderDto;
import com.ewha.heydongdong.module.model.dto.NewOrderDto;
import com.ewha.heydongdong.module.model.dto.NewOrderInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

        NewOrderInfoDto newOrderInfo = NewOrderInfoDto.builder()
                .orderAt(Timestamp.valueOf("2020-11-06 16:16:16"))
                .isNoShow(false)
                .progress(Progress.WAITING)
                .storeId(1)
                .userId("test_user")
                .totalCount(3)
                .totalPrice(8100)
                .build();
        List<MenuInNewOrderDto> menus = new ArrayList<>();
        menus.add(MenuInNewOrderDto.builder()
                .menuId(23)
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isHot(true).isSmall(true).isTumblr(false).build())
                        .build())
                .price(1500)
                .count(1)
                .build());
        menus.add(MenuInNewOrderDto.builder()
                .menuId(32)
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isHot(true).isSmall(true).isTumblr(false).build())
                        .customOption(CustomOption.builder().shotAmericano(1).build())
                        .build())
                .price(2000)
                .count(1)
                .build());
        menus.add(MenuInNewOrderDto.builder()
                .menuId(23)
                .option(Option.builder()
                        .basicOption(BasicOption.builder().isHot(false).isSmall(false).isTumblr(true).build())
                        .customOption(CustomOption.builder().shotAmericano(1).build())
                        .build())
                .price(2300)
                .count(2)
                .build());
        NewOrderDto newOrder = NewOrderDto.builder().newOrderInfo(newOrderInfo).menus(menus).build();

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("AddNewOrderRequest", "test_user"), objectMapper.valueToTree(newOrder)));

        mockMvc.perform(post("/order/add")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Get history of a store | Success")
    void getHistory_Success() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetStoreHistoryRequest", "admin"), null));

        mockMvc.perform(put("/admin/history/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get history of a store | Invalid storeId")
    void getHistory_Fail_InvalidStoreId() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetStoreHistoryRequest", "admin"), null));

        Response response = Response.builder()
                .header(ResponseHeader.builder().name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [storeId=5000]")
                        .build())
                .build();

        mockMvc.perform(put("/admin/history/5000")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }


    @Test
    @DisplayName("Get orders of a store | Success")
    void getOrders_Success() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetStoreOrdersRequest", "admin"), null));

        mockMvc.perform(put("/admin/orders/1")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get orders of a store | Invalid storeId")
    void getOrders_Fail_InvalidStoreId() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetStoreOrdersRequest", "admin"), null));

        Response response = Response.builder()
                .header(ResponseHeader.builder().name("InvalidRequestParameterException")
                        .message("InvalidRequestParameterException: Invalid request parameter [storeId=5000]")
                        .build())
                .build();

        mockMvc.perform(put("/admin/orders/5000")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }
}
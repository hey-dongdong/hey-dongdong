package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all menus of a store | Success")
    void getAllMenus_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("storeId", 1);
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetAllMenusRequest", "admin"), payload));

        mockMvc.perform(post("/menu/get-all")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get all menus of a store | Fail : Invalid storeId")
    void getAllMenus_Fail_InvalidStoreId() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("storeId", 5000);
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetAllMenusRequest", "admin"), payload));

        mockMvc.perform(post("/menu/get-all")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
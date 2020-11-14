package com.ewha.heydongdong.module.controller;

import com.ewha.heydongdong.infra.protocol.Request;
import com.ewha.heydongdong.infra.protocol.RequestHeader;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MyMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @DisplayName("Get all my menus | Success")
    void getAllMyMenus_Success() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetMyMenusRequest", "test_user"), null));

        mockMvc.perform(post("/my-menu/get-all")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get all my menus | Fail : No my menu")
    void getAllMyMenus_Fail_NoMyMenu() throws Exception {

        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("GetMyMenusRequest", "user_with_no_my_menu"), null));

        mockMvc.perform(post("/my-menu/get-all")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }



    @Test
    @DisplayName("Add my menu | Success")
    void addMyMenu_Success() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("menuInOrderId", 40);
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("AddMyMenuRequest", "test_user"), payload));

        mockMvc.perform(post("/my-menu/add")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Add my menu | Fail : My menu already exists")
    void addMyMenu_Fail_MyMenuAlreadyExists() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("menuInOrderId", 4);
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("AddMyMenuResponse", "ewha111"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("InvalidRequestFormatException")
                        .message("InvalidRequestFormatException: Invalid request format [Invalid header name=AddMyMenuResponse]")
                        .build()).build();
        mockMvc.perform(post("/my-menu/add")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }



    @Test
    @DisplayName("Remove my menu | Fail : Invalid myMenuId")
    void removeMyMenu_Fail_InvalidMyMenuId() throws Exception {

        ObjectNode payload = objectMapper.createObjectNode();
        payload.put("myMenuId", 4000);
        String content = objectMapper.writeValueAsString(new Request(
                new RequestHeader("RemoveMyMenuRequest", "test_user"), payload));

        Response response = Response.builder()
                .header(ResponseHeader.builder()
                        .name("EmptyResultDataAccessException")
                        .message("Empty result data access exception [No class com.ewha.heydongdong.module.model.domain.MyMenu entity with id 4000 exists!]")
                        .build()).build();
        mockMvc.perform(post("/my-menu/remove")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(objectMapper.valueToTree(response).toPrettyString()));
    }
}
package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.MyMenu;
import com.ewha.heydongdong.model.domain.User;
import com.ewha.heydongdong.model.exception.NoResultFromDBException;
import com.ewha.heydongdong.model.protocol.Header;
import com.ewha.heydongdong.model.protocol.Response;
import com.ewha.heydongdong.repository.MyMenuRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MyMenuService {

    @Autowired
    private MyMenuRepository myMenuRepository;

    public String getUserMyMenu(String userId) {
        List<MyMenu> myMenus = myMenuRepository.findByUser(User.builder().userId(userId).build());
        checkIfMyMenuExists(myMenus, userId);
        return buildUserMyMenuJson(userId, myMenus);
    }

    private void checkIfMyMenuExists(List<MyMenu> myMenus, String userId) {
        if (myMenus.isEmpty())
            throw new NoResultFromDBException("No myMenu for userId=" + userId);
    }

    private String buildUserMyMenuJson(String userId, List<MyMenu> myMenus) {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Header header = new Header("GetMyMenusResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("menus", objectMapper.valueToTree(myMenus));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }

    public String addUserMyMenu(String userId, Long menuInOrderId) {
        MyMenu myMenu = MyMenu.builder()
                .addAt(new Timestamp(System.currentTimeMillis()))
                .userId(userId)
                .menuInOrderId(menuInOrderId)
                .build();
        myMenuRepository.save(myMenu);
        return buildJsonResponse(userId);
    }

    private String buildJsonResponse(String userId) {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Header header = new Header("AddMyMenuResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }
}
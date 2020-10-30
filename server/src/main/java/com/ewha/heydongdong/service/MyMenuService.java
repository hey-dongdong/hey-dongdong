package com.ewha.heydongdong.service;

import com.ewha.heydongdong.model.domain.MenuInOrder;
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

        return buildJsonResponse("GetMyMenusResponse", userId, myMenus);
    }

    public String addUserMyMenu(String userId, Long menuInOrderId) {
        User user = User.builder()
                .userId(userId)
                .build();
        MenuInOrder menuInOrder = MenuInOrder.builder()
                .id(menuInOrderId)
                .build();

        MyMenu myMenu = MyMenu.builder()
                .addAt(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .menuInOrder(menuInOrder)
                .build();
        myMenuRepository.save(myMenu);
        return buildJsonResponse("AddMyMenuResponse", userId);
    }

    public String removeUserMyMenu(String userId, Long myMenuId) {
        myMenuRepository.deleteById(myMenuId);
        return buildJsonResponse("RemoveMyMenuResponse", userId);
    }

    private String buildJsonResponse(String responseName, String userId) {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Header header = new Header(responseName, userId);

        ObjectNode payload = objectMapper.createObjectNode();
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }

    private String buildJsonResponse(String responseName, String userId, List<MyMenu> myMenus) {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Header header = new Header(responseName, userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("menus", objectMapper.valueToTree(myMenus));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }

    private void checkIfMyMenuExists(List<MyMenu> myMenus, String userId) {
        if (myMenus.isEmpty())
            throw new NoResultFromDBException("No myMenu for userId=" + userId);
    }
}

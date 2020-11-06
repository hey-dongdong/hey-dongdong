package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.MyMenu;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.module.model.dto.MenuInMyMenuDto;
import com.ewha.heydongdong.module.model.dto.MyMenuDto;
import com.ewha.heydongdong.module.repository.MyMenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyMenuService {

    @Autowired
    private MyMenuRepository myMenuRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String getUserMyMenu(String userId) {
        List<MyMenu> myMenus = myMenuRepository.findByUser(User.builder().userId(userId).build());
        checkIfMyMenuExists(myMenus, userId);
        List<MyMenuDto> myMenuDto =
        return buildJsonResponse("GetMyMenusResponse", userId, myMenus);
    }

    public String addUserMyMenu(String userId, Long menuInOrderId) {
        User user = User.builder()
                .userId(userId)
                .build();

        MenuInMyMenuDto menuInOrder = MenuInMyMenuDto.builder()
                .menuInOrderId(menuInOrderId)
                .menu()
                .option()
                .price()
                .build();

        MyMenuDto myMenu = MyMenuDto.builder()
                .myMenuId()
                .addAt(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .menuInOrder(menuInOrder)
                .build();
        myMenuRepository.save(myMenu);

        return buildJsonResponse("AddMyMenuResponse", userId);
    }

    private List<MyMenuDto> buildUserMyMenu(List<MyMenu> myMenus) {
        List<MyMenuDto> myMenu = new ArrayList<>();
        for(MyMenu userMyMenu : myMenus) {
            myMenu.add(MyMenuDto.builder()
            .myMenuId()
            .addAt());
        }
    }

    public String removeUserMyMenu(String userId, Long myMenuId) {
        myMenuRepository.deleteById(myMenuId);

        return buildJsonResponse("RemoveMyMenuResponse", userId);
    }

    private String buildJsonResponse(String responseName, String userId) {
        ResponseHeader header = new ResponseHeader(responseName, userId);

        ObjectNode payload = objectMapper.createObjectNode();
        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }

    private String buildJsonResponse(String responseName, String userId, List<MyMenu> myMenus) {
        ResponseHeader header = new ResponseHeader(responseName, userId);

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

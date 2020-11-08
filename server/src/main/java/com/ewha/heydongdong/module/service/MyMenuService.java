package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.MenuInOrder;
import com.ewha.heydongdong.module.model.domain.MyMenu;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.MenuDto;
import com.ewha.heydongdong.module.model.dto.MenuInMyMenuDto;
import com.ewha.heydongdong.module.model.dto.MyMenuDto;
import com.ewha.heydongdong.module.repository.MyMenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
        checkIfMyMenusExist(myMenus, userId);
        return buildJsonResponse(userId, buildMyMenuDtoFromMyMenus(myMenus));
    }

    private List<MyMenuDto> buildMyMenuDtoFromMyMenus(List<MyMenu> myMenus) {
        List<MyMenuDto> myMenuDtos = new ArrayList<>();
        for (MyMenu myMenu : myMenus) {
            myMenuDtos.add(MyMenuDto.builder()
                    .myMenuId(myMenu.getMyMenuId())
                    .menuInOrder(MenuInMyMenuDto.builder()
                            .menu(MenuDto.builder()
                                    .menuId(myMenu.getMenuInOrder().getMenu().getMenuId())
                                    .menuName(myMenu.getMenuInOrder().getMenu().getMenuName())
                                    .imgUrl(myMenu.getMenuInOrder().getMenu().getImgUrl())
                                    .build())
                            .menuInOrderId(myMenu.getMenuInOrder().getId())
                            .option(myMenu.getMenuInOrder().getOption())
                            .price(myMenu.getMenuInOrder().getPrice())
                            .build())
                    .addAt(myMenu.getAddAt())
                    .storeName(myMenu.getMenuInOrder().getMenu().getStore().getStoreName())
                    .storeId((myMenu.getMenuInOrder().getMenu().getStore().getStoreId()))
                    .build());
        }
        return myMenuDtos;
    }

    private void checkIfMyMenusExist(List<MyMenu> myMenus, String userId) {
        if (myMenus.isEmpty())
            throw new NoResultFromDBException("No myMenu for userId=" + userId);
    }

    private String buildJsonResponse(String userId, List<MyMenuDto> myMenus) {
        ResponseHeader header = new ResponseHeader("GetMyMenusResponse", userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("menus", objectMapper.valueToTree(myMenus));
        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }

    public String addUserMyMenu(String userId, Long menuInOrderId) {
        checkIfDuplicate(userId, menuInOrderId);
        Long myMenuId = saveMyMenu(userId, menuInOrderId);
        return buildJsonResponse("AddMyMenuResponse", myMenuId);
    }

    private void checkIfDuplicate(String userId, Long menuInOrderId) {
        List<MyMenu> myMenus = myMenuRepository.findByUser(User.builder().userId(userId).build());
        if (!myMenus.isEmpty())
            for (MyMenu myMenu : myMenus)
                if (myMenu.getMenuInOrder().getId().equals(menuInOrderId))
                    throw new InvalidRequestParameterException("MyMenu already exists userId=" + userId + ",menuInOrderId=" + menuInOrderId);
    }

    private Long saveMyMenu(String userId, Long menuInOrderId) {
        String now = getCurrentTime();
        MyMenu myMenu = myMenuRepository.save(MyMenu.builder()
                .menuInOrder(MenuInOrder.builder().id(menuInOrderId).build())
                .user(User.builder().userId(userId).build())
                .addAt(Timestamp.valueOf(now))
                .build());
        return myMenu.getMyMenuId();
    }

    private String getCurrentTime() {
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(now);
    }

    public String removeUserMyMenu(String userId, Long myMenuId) {
        myMenuRepository.deleteById(myMenuId);
        return buildJsonResponse("RemoveMyMenuResponse", userId);
    }

    private String buildJsonResponse(String responseName, String userId) {
        Response response = Response.builder().header(
                ResponseHeader.builder()
                        .name(responseName)
                        .message(userId)
                        .build()).build();
        return objectMapper.valueToTree(response).toPrettyString();
    }

    private String buildJsonResponse(String responseName, Long myMenuId) {
        Response response = Response.builder().header(
                ResponseHeader.builder()
                        .name(responseName)
                        .message(String.valueOf(myMenuId))
                        .build()).build();
        return objectMapper.valueToTree(response).toPrettyString();
    }
}

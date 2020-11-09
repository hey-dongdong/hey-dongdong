package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.module.model.domain.MenuInOrder;
import com.ewha.heydongdong.module.model.domain.MyMenu;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.model.dto.MenuInOrderDto;
import com.ewha.heydongdong.module.model.dto.MyMenuDto;
import com.ewha.heydongdong.module.model.dto.SimpleMenuDto;
import com.ewha.heydongdong.module.repository.MyMenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private JsonBuilder jsonBuilder;

    @Autowired
    private ObjectMapper objectMapper;


    public String getUserMyMenu(String userId) {
        List<MyMenu> myMenus = myMenuRepository.findByUser(User.builder().userId(userId).build());
        checkIfMyMenusExist(myMenus, userId);
        List<MyMenuDto> myMenuDtos = buildMyMenuDtosFromMyMenus(myMenus);
        return buildMyMenusJsonResponse(userId, myMenuDtos);
    }

    private void checkIfMyMenusExist(List<MyMenu> myMenus, String userId) {
        if (myMenus.isEmpty())
            throw new NoResultFromDBException("No myMenu for userId=" + userId);
    }

    private List<MyMenuDto> buildMyMenuDtosFromMyMenus(List<MyMenu> myMenus) {
        List<MyMenuDto> myMenuDtos = new ArrayList<>();
        for (MyMenu myMenu : myMenus) {
            myMenuDtos.add(MyMenuDto.builder()
                    .myMenuId(myMenu.getMyMenuId())
                    .menuInOrder(MenuInOrderDto.builder()
                            .menu(SimpleMenuDto.builder()
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

    private String buildMyMenusJsonResponse(String userId, List<MyMenuDto> myMenus) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetMyMenusResponse", userId),
                jsonBuilder.buildResponsePayloadFromObject("menus", myMenus)
        );
    }


    public String addUserMyMenu(String userId, Long menuInOrderId) {
        checkIfMenuAlreadyLiked(userId, menuInOrderId);
        Long myMenuId = saveMyMenu(userId, menuInOrderId);
        return jsonBuilder.buildJsonWithHeader("AddMyMenuResponse", String.valueOf(myMenuId));
    }

    private void checkIfMenuAlreadyLiked(String userId, Long menuInOrderId) {
        List<MyMenu> myMenus = myMenuRepository.findByUser(User.builder().userId(userId).build());
        if (!myMenus.isEmpty())
            for (MyMenu myMenu : myMenus)
                if (myMenu.getMenuInOrder().getId().equals(menuInOrderId))
                    throw new InvalidRequestParameterException("MyMenu already exists userId=" + userId + ",menuInOrderId=" + menuInOrderId);
    }

    private Long saveMyMenu(String userId, Long menuInOrderId) {
        MyMenu myMenu = myMenuRepository.save(MyMenu.builder()
                .menuInOrder(MenuInOrder.builder().id(menuInOrderId).build())
                .user(User.builder().userId(userId).build())
                .addAt(getCurrentTime())
                .addAt(getCurrentTime())
                .build());
        return myMenu.getMyMenuId();
    }

    private Timestamp getCurrentTime() {
        Date nowDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowStr = format.format(nowDate);
        return Timestamp.valueOf(nowStr);
    }


    public String removeUserMyMenu(String userId, Long myMenuId) {
        myMenuRepository.deleteById(myMenuId);
        return jsonBuilder.buildJsonWithHeader("RemoveMyMenuResponse", userId);
    }
}

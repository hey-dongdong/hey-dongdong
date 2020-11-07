package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.model.dto.MenuDetailDto;
import com.ewha.heydongdong.module.repository.MenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String getAllMenus(Integer storeId) {
        List<Menu> menus = menuRepository.findByStore(Store.builder().storeId(storeId).build());
        checkIfMenusExist(storeId, menus);
        return buildJsonResponse(buildMenuDtoFromMenu(menus));
    }

    private void checkIfMenusExist(Integer storeId, List<Menu> menus) {
        if (menus.isEmpty())
            throw new InvalidRequestParameterException("storeId=" + storeId);
    }

    private List<MenuDetailDto> buildMenuDtoFromMenu(List<Menu> menus) {
        List<MenuDetailDto> menuDetails = new ArrayList<>();
        for (Menu menu : menus) {
            menuDetails.add(MenuDetailDto.builder()
                    .menuId(menu.getMenuId())
                    .menuName(menu.getMenuName())
                    .imgUrl(menu.getImgUrl())
                    .categoryId(menu.getCategory().getCategoryId())
                    .smallHotPrice(menu.getPrice().getSmallHotPrice())
                    .smallIcePrice(menu.getPrice().getSmallIcePrice())
                    .largeHotPrice(menu.getPrice().getLargeHotPrice())
                    .largeIcePrice(menu.getPrice().getLargeIcePrice())
                    .build());
        }
        return menuDetails;
    }

    private String buildJsonResponse(List<MenuDetailDto> menus) {
        ResponseHeader header = new ResponseHeader();
        header.setName("GetAllMenusResponse");

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("menus", objectMapper.valueToTree(menus));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }
}

package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.JsonBuilder;
import com.ewha.heydongdong.infra.exception.InvalidRequestParameterException;
import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.model.dto.MenuDto;
import com.ewha.heydongdong.module.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private JsonBuilder jsonBuilder;


    public String getAllMenus(Integer storeId) {
        List<Menu> menusInStore = findAllMenusOfStore(storeId);
        List<MenuDto> menuDtos = buildMenuDtoFromMenu(menusInStore);
        return buildMenusJsonResponse(storeId, menuDtos);
    }

    private List<Menu> findAllMenusOfStore(Integer storeId) {
        List<Menu> menusInStore = menuRepository.findByStore(Store.builder().storeId(storeId).build());
        checkIfMenusExist(storeId, menusInStore);
        return menusInStore;
    }

    private void checkIfMenusExist(Integer storeId, List<Menu> menusInStore) {
        if (menusInStore.isEmpty())
            throw new InvalidRequestParameterException("storeId=" + storeId);
    }

    private List<MenuDto> buildMenuDtoFromMenu(List<Menu> menusInStore) {
        List<MenuDto> menus = new ArrayList<>();
        for (Menu menu : menusInStore) {
            menus.add(MenuDto.builder()
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
        return menus;
    }

    private String buildMenusJsonResponse(int storeId, List<MenuDto> menuDtos) {
        return jsonBuilder.buildJsonWithHeaderAndPayload(
                jsonBuilder.buildResponseHeader("GetAllMenusResponse", String.valueOf(storeId)),
                jsonBuilder.buildResponsePayloadFromObject("menus", menuDtos)
        );
    }
}

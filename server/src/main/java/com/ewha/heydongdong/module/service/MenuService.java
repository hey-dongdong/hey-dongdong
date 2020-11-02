package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.Menu;
import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.repository.MenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String getAllMenus(Integer storeId) {
        List<Menu> menus = menuRepository.findByStore(Store.builder().storeId(storeId).build());

        return buildJsonResponse("GetAllMenusResponse", menus);
    }

    private String buildJsonResponse(String responseName, List<Menu> menus) {
        ResponseHeader header = new ResponseHeader();
        header.setName(responseName);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("menus", objectMapper.valueToTree(menus));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }
}

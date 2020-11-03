package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.Store;
import com.ewha.heydongdong.module.repository.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String getStoreInfo(String storeName) {
        Store store = storeRepository.findByStoreName(storeName);

        return buildJsonResponse("StoreInfoRequest", store);
    }

    private String buildJsonResponse(String responseName, Store store) {
        ResponseHeader header = new ResponseHeader();
        header.setName(responseName);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("store", objectMapper.valueToTree(store));
        Response response = new Response(header, payload);

        return objectMapper.valueToTree(response).toPrettyString();
    }
}

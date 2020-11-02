package com.ewha.heydongdong.module.service;

import com.ewha.heydongdong.infra.exception.NoResultFromDBException;
import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.ewha.heydongdong.module.model.domain.MyMenu;
import com.ewha.heydongdong.module.model.domain.User;
import com.ewha.heydongdong.module.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String getUserNoShowCount(String userId) {
        List<User> foundUsers = userRepository.findByUserId(userId);
        if (foundUsers.isEmpty())
            throw new NoResultFromDBException("No such user for userId=" + userId);
        int noShowCount = foundUsers.get(0).getNoShowCount();

        return buildJsonResponse("GetNoShowCountResponse", userId, noShowCount);
    }

    private String buildJsonResponse(String responseName, String userId, Integer noShowCount) {
        ResponseHeader header = new ResponseHeader(responseName, userId);

        ObjectNode payload = objectMapper.createObjectNode();
        payload.set("noShowCount", objectMapper.valueToTree(noShowCount));
        Response response = new Response(header, payload);
        return objectMapper.valueToTree(response).toPrettyString();
    }
}

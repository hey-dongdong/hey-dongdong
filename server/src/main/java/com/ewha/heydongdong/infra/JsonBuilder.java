package com.ewha.heydongdong.infra;

import com.ewha.heydongdong.infra.protocol.Response;
import com.ewha.heydongdong.infra.protocol.ResponseHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonBuilder {

    @Autowired
    private ObjectMapper objectMapper;

    public String buildJsonWithHeader(String name, String message) {
        return objectMapper.valueToTree(Response.builder()
                .header(ResponseHeader.builder()
                        .name(name)
                        .message(message)
                        .build())
                .build())
                .toPrettyString();
    }

    public ResponseHeader buildResponseHeader(String name, String message) {
        return ResponseHeader.builder()
                .name(name)
                .message(message)
                .build();
    }

    public ObjectNode buildResponsePayloadFromText(String fieldName, Object content) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.put(fieldName, String.valueOf(content));
        return payload;
    }

    public ObjectNode buildResponsePayloadFromText(String[] fieldNames, Object[] contents) {
        ObjectNode payload = objectMapper.createObjectNode();
        for (int i = 0; i < fieldNames.length; i++)
            payload.put(fieldNames[i], String.valueOf(contents[i]));
        return payload;
    }

    public ObjectNode buildResponsePayloadFromObject(String fieldName, Object object) {
        ObjectNode payload = objectMapper.createObjectNode();
        payload.set(fieldName, objectMapper.valueToTree(object));
        return payload;
    }

    public ObjectNode buildResponsePayloadFromObject(String[] fieldNames, Object[] objects) {
        ObjectNode payload = objectMapper.createObjectNode();
        for (int i = 0; i < fieldNames.length; i++)
            payload.set(fieldNames[i], objectMapper.valueToTree(objects[i]));
        return payload;
    }

    public String buildJsonWithHeaderAndPayload(ResponseHeader header, ObjectNode payload) {
        return objectMapper.valueToTree(Response.builder()
                .header(header)
                .payload(payload)
                .build())
                .toPrettyString();
    }
}

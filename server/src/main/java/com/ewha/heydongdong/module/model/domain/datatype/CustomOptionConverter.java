package com.ewha.heydongdong.module.model.domain.datatype;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class CustomOptionConverter implements AttributeConverter<CustomOption, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    public String convertToDatabaseColumn(CustomOption customOption) {
        if (customOption == null)
            return null;

        try {
            return objectMapper.writeValueAsString(customOption);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomOption convertToEntityAttribute(String jsonStr) {
        if (jsonStr == null)
            return null;

        try {
            return objectMapper.readValue(jsonStr, CustomOption.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

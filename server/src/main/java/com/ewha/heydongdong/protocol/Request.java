package com.ewha.heydongdong.protocol;

import com.ewha.heydongdong.exception.InvalidRequestException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class Request {

    private Header header;
    private JsonNode payload;

    public void validateHeader(String expectedName) throws InvalidRequestException {
        validateNotNull();
        validateName(expectedName);
    }

    private void validateNotNull() {
        if (getHeader() == null) {
            String msg = "invalid-request : null-header";
            throw new InvalidRequestException(msg);
        }
    }

    private void validateName(String expectedName) throws InvalidRequestException {
        if (!expectedName.equals(this.header.getName())) {
            String msg = "invalid-request : current-name=" + this.header.getName();
            throw new InvalidRequestException(msg);
        }
    }

    public void validatePayload() throws InvalidRequestException {
        if (getPayload().isEmpty()) {
            String msg = "invalid-request : empty-payload";
            throw new InvalidRequestException(msg);
        }
    }
}

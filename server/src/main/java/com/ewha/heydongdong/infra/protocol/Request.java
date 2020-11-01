package com.ewha.heydongdong.infra.protocol;

import com.ewha.heydongdong.infra.exception.InvalidRequestFormatException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private Header header;

    private JsonNode payload;

    public void validateHeader(String expectedName) throws InvalidRequestFormatException {
        validateNotNull();
        validateName(expectedName);
        validateUserId();
    }

    private void validateNotNull() {
        if (this.header == null) {
            String msg = "null-header";
            throw new InvalidRequestFormatException(msg);
        }
    }

    private void validateName(String expectedName) throws InvalidRequestFormatException {
        if (!expectedName.equals(this.header.getName())) {
            String msg = "invalid-header-name=" + this.header.getName();
            throw new InvalidRequestFormatException(msg);
        }
    }

    private void validateUserId() {
        if (this.header.getUserId() == null) {
            String msg = "null-header-userid";
            throw new InvalidRequestFormatException(msg);
        }
    }

    public void validatePayload() throws InvalidRequestFormatException {
        if (getPayload() == null) {
            String msg = "null-payload";
            throw new InvalidRequestFormatException(msg);
        } else if (getPayload().isEmpty()) {
            String msg = "empty-payload";
            throw new InvalidRequestFormatException(msg);
        }
    }
}

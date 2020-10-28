package com.ewha.heydongdong.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {

    private Header header;
    private JsonNode payload;
}

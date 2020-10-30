package com.ewha.heydongdong.model.protocol;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private Header header;
    private ObjectNode payload;
}

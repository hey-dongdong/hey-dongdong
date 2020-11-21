package com.ewha.heydongdong.infra.protocol;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {

    private ResponseHeader header;
    private ObjectNode payload;

}

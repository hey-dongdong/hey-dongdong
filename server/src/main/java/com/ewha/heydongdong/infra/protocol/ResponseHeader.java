package com.ewha.heydongdong.infra.protocol;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseHeader {

    private String name;
    private String message;

}



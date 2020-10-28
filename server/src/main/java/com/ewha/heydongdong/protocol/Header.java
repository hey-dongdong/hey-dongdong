package com.ewha.heydongdong.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class Header {

    private String name;
    private String userId;
}

package com.ewha.heydongdong.module.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDto {

    private String userId;
    private String userName;
    private String phone;

}

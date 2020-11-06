package com.ewha.heydongdong.module.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserInStoreHistoryDto {

    private String userId;
    private String userName;
    private String phone;
}

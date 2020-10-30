package com.ewha.heydongdong.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UserHistoryDetailDto {

    private UserHistoryDto orderInfo;

    private List<MenuInHistoryDetailDto> menus;
}

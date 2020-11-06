package com.ewha.heydongdong.module.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class StoreHistoryDetailDto {
    private StoreHistoryDto orderInfo;
    private List<MenuInHistoryDetailDto> menus;
}


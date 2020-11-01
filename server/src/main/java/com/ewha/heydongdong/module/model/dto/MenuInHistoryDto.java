package com.ewha.heydongdong.module.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuInHistoryDto {
    private Integer menuId;
    private String menuName;
    private String menuThumbnail;
}

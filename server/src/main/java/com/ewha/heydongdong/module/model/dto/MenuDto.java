package com.ewha.heydongdong.module.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MenuDto {

    private Integer menuId;
    private Integer categoryId;
    private String menuName;
    private Integer smallHotPrice;
    private Integer smallIcePrice;
    private Integer largeHotPrice;
    private Integer largeIcePrice;
    private String imgUrl;
}

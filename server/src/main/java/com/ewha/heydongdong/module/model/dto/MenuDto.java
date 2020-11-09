package com.ewha.heydongdong.module.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

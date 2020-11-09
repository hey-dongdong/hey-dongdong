package com.ewha.heydongdong.module.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SimpleMenuDto {

    private Integer menuId;
    private String menuName;
    private String imgUrl;

}

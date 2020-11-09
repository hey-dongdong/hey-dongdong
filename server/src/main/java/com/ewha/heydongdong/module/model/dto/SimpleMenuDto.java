package com.ewha.heydongdong.module.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMenuDto {

    private Integer menuId;
    private String menuName;
    private String imgUrl;

}

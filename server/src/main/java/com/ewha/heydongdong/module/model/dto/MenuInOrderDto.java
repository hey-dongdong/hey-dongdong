package com.ewha.heydongdong.module.model.dto;


import com.ewha.heydongdong.module.model.domain.datatype.Option;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuInOrderDto {

    private Long menuInOrderId;
    private SimpleMenuDto menu;
    private Option option;
    private Integer price;
    private Integer count;
    private Boolean menuLiked;
    private Long myMenuId;  // TODO

}

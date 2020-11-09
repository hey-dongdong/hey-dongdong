package com.ewha.heydongdong.module.model.dto;


import com.ewha.heydongdong.module.model.domain.datatype.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MenuInOrderDto {

    private Long menuInOrderId;
    private SimpleMenuDto menu;
    private Option option;
    private Integer price;

}

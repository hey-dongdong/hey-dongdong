package com.ewha.heydongdong.module.model.dto;

import com.ewha.heydongdong.module.model.domain.datatype.Option;
import com.ewha.heydongdong.module.model.domain.datatype.Price;
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
    private MenuDto menu;
    private Option option;
    private Price price;

}

package com.ewha.heydongdong.module.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderDto {
    private NewOrderInfoDto newOrderInfo;
    private List<MenuInNewOrderDto> menus;
}

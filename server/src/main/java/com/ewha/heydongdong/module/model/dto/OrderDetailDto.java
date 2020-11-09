package com.ewha.heydongdong.module.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private OrderDto orderInfo;
    private List<MenuInOrderDto> menus;

}

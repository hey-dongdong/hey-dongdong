package com.ewha.heydongdong.module.model.dto;

import com.ewha.heydongdong.module.model.domain.datatype.Option;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInNewOrderDto {

    private Integer menuId;
    private Option option;
    private Integer price;
    private Integer count;

}

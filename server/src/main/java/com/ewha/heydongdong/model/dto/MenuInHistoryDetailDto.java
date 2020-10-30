package com.ewha.heydongdong.model.dto;

import com.ewha.heydongdong.model.domain.datatype.Option;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MenuInHistoryDetailDto {
    private Long menuInOrderId;
    private MenuInHistoryDto menu;
    private Option option;
    private Integer price;
    private Integer count;
    private Boolean menuLiked;
}

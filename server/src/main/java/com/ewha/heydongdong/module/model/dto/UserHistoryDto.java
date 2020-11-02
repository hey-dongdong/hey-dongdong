package com.ewha.heydongdong.module.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
public class UserHistoryDto {
    private Long orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp orderAt;
    private Integer totalPrice;
    private Integer totalCount;
    private MenuInHistoryDto menu;
    private StoreInHistoryDto store;
}

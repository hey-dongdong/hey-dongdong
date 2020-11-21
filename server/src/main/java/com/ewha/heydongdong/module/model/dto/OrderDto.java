package com.ewha.heydongdong.module.model.dto;

import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp orderAt;
    private Integer totalPrice;
    private Integer totalCount;
    private Progress progress;
    private boolean isNoShow;

    private SimpleMenuDto menu;
    private SimpleStoreDto store;
    private SimpleUserDto user;

}

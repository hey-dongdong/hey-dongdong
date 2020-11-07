package com.ewha.heydongdong.module.model.dto;

import com.ewha.heydongdong.module.model.domain.datatype.Progress;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderInfoDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp orderAt;
    private Progress progress;
    private Integer totalCount;
    private Integer totalPrice;
    private Boolean isNoShow;
    private Integer storeId;
    private String userId;
}

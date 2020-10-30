package com.ewha.heydongdong.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreInHistoryDto {

    private Integer storeId;
    private String storeName;
}

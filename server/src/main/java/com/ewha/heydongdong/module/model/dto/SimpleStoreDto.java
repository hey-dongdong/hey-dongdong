package com.ewha.heydongdong.module.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleStoreDto {

    private Integer storeId;
    private String storeName;

}

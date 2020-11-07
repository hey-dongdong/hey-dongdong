package com.ewha.heydongdong.module.model.domain.datatype;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
public class Price {
    @Column(name = "small_hot_price")
    private Integer smallHotPrice;

    @Column(name = "small_ice_price")
    private Integer smallIcePrice;

    @Column(name = "large_hot_price")
    private Integer largeHotPrice;

    @Column(name = "large_ice_price")
    private Integer largeIcePrice;
}

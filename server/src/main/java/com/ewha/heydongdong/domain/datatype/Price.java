package com.ewha.heydongdong.domain.datatype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Price {

    @Column(name = "small_hot_price")
    private Integer smallHotPrice;

    @Column(name = "small_ice_price")
    private Integer smallIcePrice;

    @Column(name = "large_hot_price")
    private Integer largeHotPrice;

    @Column(name = "large_ice_price")
    private Integer largeIcePrice;

    public boolean isHotAvailable() {
        return smallHotPrice != null || largeHotPrice != null;
    }

    public boolean isIceAvailable() {
        return smallIcePrice != null || largeIcePrice != null;
    }
}

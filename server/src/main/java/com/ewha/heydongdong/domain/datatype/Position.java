package com.ewha.heydongdong.domain.datatype;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@ToString
public class Position {

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Builder
    public Position(Double latitude, Double longitude) {
        Assert.notNull(latitude, "Latitude must not be null");
        Assert.notNull(longitude, "Longitude must not be null");

        this.latitude = latitude;
        this.longitude = longitude;
    }
}

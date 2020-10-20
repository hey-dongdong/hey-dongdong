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
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Builder
    public Position(double latitude, double longitude) {
        Assert.notNull(latitude, "Latitude must not be null");
        Assert.notNull(longitude, "Longitude must not be null");

        this.latitude = latitude;
        this.longitude = longitude;
    }
}

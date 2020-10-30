package com.ewha.heydongdong.domain.datatype;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
@ToString
public class BasicOption {

    @Column(name = "is_hot", nullable = false)
    private Boolean isHot;

    @Column(name = "is_small", nullable = false)
    private Boolean isSmall;

    @Column(name = "is_tumblr", nullable = false)
    private Boolean isTumblr;

    @Builder
    public BasicOption(Boolean isHot, Boolean isSmall, Boolean isTumblr) {
        Assert.notNull(isHot, "IsHot must not be null");
        Assert.notNull(isSmall, "IsSmall must not be null");
        Assert.notNull(isTumblr, "IsTumblr must not be null");

        this.isHot = isHot;
        this.isSmall = isSmall;
        this.isTumblr = isTumblr;
    }
}

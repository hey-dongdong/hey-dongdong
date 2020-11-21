package com.ewha.heydongdong.module.model.domain.datatype;

import com.ewha.heydongdong.infra.converter.CustomOptionConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Getter
@NoArgsConstructor
@Embeddable
@ToString
public class Option {

    @Embedded
    private BasicOption basicOption;

    @Column(name = "custom_options")
    @Convert(converter = CustomOptionConverter.class)
    private CustomOption customOption;

    @Builder
    public Option(BasicOption basicOption, CustomOption customOption) {
        Assert.notNull(basicOption, "BasicOption must not be null");

        this.basicOption = basicOption;
        this.customOption = customOption;
    }
}

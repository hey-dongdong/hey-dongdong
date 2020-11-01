package com.ewha.heydongdong.module.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "id")
    private Integer categoryId;

    @Column(name = "name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public Category(Integer categoryId, String categoryName) {
        Assert.notNull(categoryId, "CategoryId must not be null");

        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category(categoryId=" + categoryId
                + ", categoryName=" + categoryName
                + ")";
    }
}

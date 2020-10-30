package com.ewha.heydongdong.domain;

import com.ewha.heydongdong.domain.datatype.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @Column(name = "id")
    private Integer menuId;

    @Column(name = "name", nullable = false)
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Embedded
    private Price price;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Override
    public String toString() {
        return "Menu(menuId=" + menuId
                + ", menuName=" + menuName
                + ", category=" + category.getCategoryName()
                + ", store=" + store.getStoreName()
                + ", price=" + price
                + ", imgUrl=" + imgUrl
                + ")";
    }
}

package com.ewha.heydongdong.model.domain;

import com.ewha.heydongdong.model.domain.datatype.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menus_in_orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuInOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @Column(name = "price")
    private Integer price;

    @Embedded
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public String toString() {
        return "\nMenuInOrder(" +
                "id=" + id +
                ", count=" + count +
                ", price=" + price +
                ", option=" + option +
                ", menu=" + menu.getMenuName() +
                ", order=(" + order.getOrderId() + "," + order.getUser().getUserName() + ")" +
                ")";
    }
}

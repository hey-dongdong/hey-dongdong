package com.ewha.heydongdong.domain;

import com.ewha.heydongdong.domain.datatype.Progress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "order_at")
    private Timestamp orderAt;

    @Column(name = "progress")
    @Enumerated(EnumType.STRING)
    private Progress progress;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "is_no_show")
    private Boolean isNoShow;

    // TODO [지우] 썸네일 추가?

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<MenuInOrder> menus = new ArrayList<>();

    @Builder
    public Order(Long orderId, User user, Store store, Timestamp orderAt, Progress progress, Integer totalPrice, Boolean isNoShow) {
        Assert.notNull(user, "User must not be null");
        Assert.notNull(store, "Store must not be null");
        Assert.notNull(orderAt, "OrderAt must not be null");
        Assert.notNull(progress, "Progress must not be null");
        Assert.notNull(totalPrice, "TotalPrice must not be null");
        Assert.notNull(isNoShow, "IsNoShow must not be null");

        this.orderId = orderId;
        this.user = user;
        this.store = store;
        this.orderAt = orderAt;
        this.progress = progress;
        this.totalPrice = totalPrice;
        this.isNoShow = isNoShow;
    }

    @Override
    public String toString() {
        return "\nOrder(orderId=" + orderId
                + ", user=(" + user.getUserId() + "," + user.getUserName()
                + "), store=(" + store.getStoreId() + "," + store.getStoreName()
                + "), orderAt=" + orderAt
                + ", progress=" + progress
                + ", totalPrice=" + totalPrice
                + ", isNoShow=" + isNoShow
                + ")";
    }
}


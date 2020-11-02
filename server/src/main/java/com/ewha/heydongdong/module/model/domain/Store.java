package com.ewha.heydongdong.module.model.domain;

import com.ewha.heydongdong.module.model.domain.datatype.Position;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor
public class Store {
    @Id
    @Column(name = "id", nullable = false)
    private Integer storeId;

    @Column(name = "name", nullable = false)
    private String storeName;

    @Column(name = "location", nullable = false)
    private String location;

    @Embedded
    private Position position;

    @Column(name = "open_time", nullable = false)
    private String openTime;

    @Column(name = "tel", nullable = false)
    private String tel;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Store(Integer storeId, String storeName, String location, Position position, String openTime, String tel) {
        Assert.notNull(storeId, "StoreId must not be null");

        this.storeId = storeId;
        this.storeName = storeName;
        this.location = location;
        this.position = position;
        this.openTime = openTime;
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Store(storeId=" + storeId
                + ", storeName=" + storeName
                + ", location=" + location
                + ", position=" + position
                + ", openTime=" + openTime
                + ", tel=" + tel
                + ")";
    }
}

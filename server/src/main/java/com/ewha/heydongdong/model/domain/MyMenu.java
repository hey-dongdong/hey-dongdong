package com.ewha.heydongdong.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "my_menus")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMenu {
    @Id
    @Column(name = "id", nullable = false)
    private Long myMenuId;

    @Column(name = "add_at")
    private Timestamp addAt;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "menu_in_order_id", nullable = false)
    private Long menuInOrderId;

    @Override
    public String toString() {
        return "MyMenu(myMenuId=" + myMenuId
                + ", addAt=" + addAt
                + ", userId=" + userId
                + ", menuInOrderId=" + menuInOrderId
                + ")";
    }
}

package com.ewha.heydongdong.module.model.domain;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myMenuId;

    @Column(name = "add_at")
    private Timestamp addAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_in_order_id", nullable = false)
    private MenuInOrder menuInOrder;

    @Override
    public String toString() {
        return "MyMenu(myMenuId=" + myMenuId
                + ", addAt=" + addAt
                + ", userId=" + user.getUserId()
                + ", menuInOrderId=" + menuInOrder.getId()
                + ")";
    }
}

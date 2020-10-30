package com.ewha.heydongdong.domain;

import com.ewha.heydongdong.domain.datatype.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

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

    @Builder
    public MyMenu(Long myMenuId, Timestamp addAt, String userId, Long menuInOrderId) {
        Assert.notNull(myMenuId, "myMenuId must not be null");
        Assert.notNull(addAt, "addAt must not be null");
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(menuInOrderId, "menuInOrderId must not be null");

        this.myMenuId = myMenuId;
        this.addAt = addAt;
        this.userId = userId;
        this.menuInOrderId = menuInOrderId;
    }

    @Override
    public String toString() {
        return "MyMenu(myMenuId=" + myMenuId
                + ", addAt=" + addAt
                + ", userId=" + userId
                + ", menuInOrderId=" + menuInOrderId
                + ")";
    }
}

package com.ewha.heydongdong.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "no_show_count")
    private Integer noShowCount;

    @Column(name = "ban_at")
    private Timestamp banAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<MyMenu> myMenus = new ArrayList<MyMenu>();

    @Builder
    public User(String userId, String userName, String email, String phone, Integer noShowCount, Timestamp banAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.noShowCount = noShowCount;
        this.banAt = banAt;
    }

    // Default values
    public static class UserBuilder {
        private Integer noShowCount = 0;
    }
}

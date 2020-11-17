package com.ewha.heydongdong.module.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "name")
    private String userName;

    public String getUserName() {
        return userName;
    }

    @Setter
    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "no_show_count")
    private Integer noShowCount;

    @Column(name = "ban_at")
    private Timestamp banAt;

    @Setter
    @Column(name = "email_check_token")
    private String emailCheckToken;

    @Setter
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Setter
    @Column(name = "device_token")
    private String deviceToken;

    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyMenu> myMenus = new ArrayList<>();

    @Builder
    public User(String userId, String userName, String password, String email,
                String phone, Integer noShowCount, Timestamp banAt,
                String emailCheckToken, Boolean isEmailVerified, List<String> roles) {
        Assert.hasText(userId, "UserId must not be empty");

        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.noShowCount = noShowCount;
        this.banAt = banAt;
        this.emailCheckToken = emailCheckToken;
        this.isEmailVerified = isEmailVerified;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

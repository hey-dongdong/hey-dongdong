package com.ewha.heydongdong.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

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

    @Column(name = "email_check_token")
    private String emailCheckToken;

    @Setter
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MyMenu> myMenus = new ArrayList<>();

    @Builder
    public User(String userId, String userName, String password, String email,
                String phone, Integer noShowCount, Timestamp banAt,
                String emailCheckToken, Boolean isEmailVerified) {
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
    }

    public SimpleMailMessage generateVerifyEmail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(this.email);
        mailMessage.setSubject("헤이동동 회원 가입을 위한 인증 메일입니다.");
        mailMessage.setText("/check-email-token/" + this.email + "/" + this.emailCheckToken);
        return mailMessage;
    }

    // Default values
    public static class UserBuilder {
        private Integer noShowCount = 0;
    }
}

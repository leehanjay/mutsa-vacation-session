package com.mutsa.delivery.user.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "credit", nullable = false)
    private Long credit;

    @Version
    @Column(name = "version", nullable = false)
    private Long version; // 낙관적 락: 동시에 크레딧을 변경할 때 lost update 방지

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String password, String nickname, Long credit) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.credit = credit;
    }

    public static User createNew(String email, String password, String nickname) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email must not be blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password must not be blank");
        }
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("nickname must not be blank");
        }
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .credit(0L)
                .build();
    }
    public void updateCredit(Long creditBalance){
        this.credit = creditBalance;
    }
}

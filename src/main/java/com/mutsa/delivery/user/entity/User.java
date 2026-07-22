package com.mutsa.delivery.user.entity;

import com.mutsa.delivery.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
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

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_id", unique = true)
    private String socialId;

    @Column(name = "credit", nullable = false)
    private Long credit;

    @Version
    @Column(name = "version", nullable = false)
    private Long version; // 낙관적 락: 동시에 크레딧을 변경할 때 lost update 방지

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String password, String nickname, Long credit, SocialType socialType, String socialId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.credit = credit;
        this.socialType = socialType;
        this.socialId = socialId;
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
                .socialType(null)
                .socialId(null)
                .build();
    }

    public static User createSocial(String email, String nickname, SocialType socialType, String socialId) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("nickname must not be blank");
        }
        if (socialType == null) {
            throw new IllegalArgumentException("socialType must not be blank");
        }
        if (socialId == null || socialId.isBlank()) {
            throw new IllegalArgumentException("socialId must not be blank");
        }
        return User.builder()
                .email(email)
                .nickname(nickname)
                .credit(0L)
                .socialType(socialType)
                .socialId(socialId)
                .build();
    }

    public void updateCredit(Long creditBalance){
        this.credit = creditBalance;
    }
}

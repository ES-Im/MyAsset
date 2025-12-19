package dev.es.myasset.domain;

import dev.es.myasset.application.provided.OAuthUserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    UserInfo hongInfo;
    User hongUser;
    LocalDateTime current = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        UserInfo hongInfo = OAuthUserInfo.of("홍길동", ProviderType.GOOGLE, "gildong@google.com");
        hongUser = User.register(hongInfo.getUserKey(), current);
    }

    @Test
    @DisplayName("register -> ACTIVE")
    void activateUser() {
        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(hongUser.getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("ACTIVE → DORMANT")
    void markDormant() {
        hongUser.markDormant(current.plusDays(366));

        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.DORMANT);
    }

    @Test
    @DisplayName("ACTIVE → WITHDRAW_PENDING")
    void requestWithdraw() {
        hongUser.requestWithdraw(current);

        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.WITHDRAW_PENDING);
    }

    @Test
    @DisplayName("WITHDRAWN_PENDING / DORMANT → WITHDRAW_PENDING")
    void requestActive() {
        hongUser.requestWithdraw(current);
        hongUser.requestActive(current);

        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.ACTIVE);

        hongUser.markDormant(current.plusDays(366));
        hongUser.requestActive(current);

        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("WITHDRAW_PENDING → WITHDRAWN")
    void markWithdraw() {
        hongUser.requestWithdraw(current);
        hongUser.markWithdraw(LocalDateTime.now().plusDays(31));

        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.WITHDRAWN);
    }
    
    @Test
    void validateEmail() {

        // invalid email
         Assertions.assertThatThrownBy(()->
                 OAuthUserInfo.of("홍길동", ProviderType.GOOGLE, "invalid email")
         ).isInstanceOf(IllegalArgumentException.class);

        // valid email
        OAuthUserInfo.of("홍길동", ProviderType.GOOGLE, "hong@google.co.kr");

    }

    // to-do : WITHDRAW_PENDING → USER, USERINFO 개인정보 물리적 삭제
}
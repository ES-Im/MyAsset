package dev.es.myasset.domain;

import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static dev.es.myasset.domain.MemberFixture.*;
import static dev.es.myasset.domain.MemberFixture.createUserInfoRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserTest {

    @Autowired UserInfoRepository userInfoRepository;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager entityManager;

    UserInfo hongInfo;
    User hongUser;
    LocalDateTime current = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        hongInfo = userInfoRepository.save(createUserInfoRegisterRequest());

        hongUser = createUserRegisterRequest();

        hongUser.linkUserInfo(hongInfo);
        hongUser = userRepository.save(hongUser);

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

//        // invalid email
//         Assertions.assertThatThrownBy(()->
//                 createUserRegisterRequest(createUserInfoRegisterRequest("invalid email"))
//         ).isInstanceOf(IllegalArgumentException.class);


    }

    // to-do : WITHDRAW_PENDING → USER, USERINFO 개인정보 물리적 삭제
}
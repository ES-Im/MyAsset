package dev.es.myasset.application.provided;

import dev.es.myasset.application.UserService;
import dev.es.myasset.application.required.OAuthUserInfo;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserRegisterTest {

    @Autowired UserRepository userRepository;
    @Autowired EntityManager entityManager;


    User user;

//    @Test
    @BeforeEach
    void register() {
        UserRegister register = new UserService(userRepository);

        user = register.registerFromOAuth(
                new OAuthUserInfo("yeongjin", ProviderType.NAVER, "meotjdayeonjinah@naver.com")
        );

        System.out.println(user.toString());

        entityManager.flush();
        entityManager.clear();

    }
    
    @Test
    void checkDomainRule() {
        User savedUser = userRepository.findById(user.getUserKey());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getRole()).isEqualTo(UserRole.USER);
        assertThat(savedUser.getWithdrawReqAt()).isNull();
        assertThat(savedUser.getLastLoginAt()).isNotNull();
    }


    





}
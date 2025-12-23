package dev.es.myasset.application.provided;

import dev.es.myasset.adapter.security.oauth.GoogleOAuthUserInfo;
import dev.es.myasset.application.UserService;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> attribute =  new HashMap<>();
        attribute.put("sub", "1231");
        attribute.put("email", "kim@gmail.com");
        attribute.put("name", "kim");

        OAuth2UserInfo providedInfo = new GoogleOAuthUserInfo(attribute);
        user = register.registerFromOAuth(providedInfo);

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
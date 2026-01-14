//package dev.es.myasset.application.required;
//
//import dev.es.myasset.domain.shared.Email;
//import dev.es.myasset.domain.user.*;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static dev.es.myasset.domain.UserFixture.*;
//import static dev.es.myasset.domain.UserFixture.createUserInfoRegisterRequest;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional
//@SpringBootTest
//class UserRepositoryTest {
//
//    @Autowired UserRepository userRepository;
//    @Autowired UserInfoRepository userInfoRepository;
//    @Autowired EntityManager entityManager;
//
//    UserInfo hongInfo;
//    User hongUser;
//
//    @BeforeEach
////    @Test
//    void setUp() {
//        hongInfo = userInfoRepository.save(createUserInfoRegisterRequest());
//        entityManager.flush();
//
//        hongUser = createUserRegisterRequest();
//        hongUser.linkUserInfo(hongInfo);
//
//        hongUser = userRepository.save(hongUser);
//
//        System.out.println(hongUser);
//        entityManager.flush();
//    }
//
//    @Test
//    void createUser() {
//
//        assertThat(hongUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
//        assertThat(hongUser.getCreatedAt()).isNotNull();
//        assertThat(hongUser.getRole()).isEqualTo(UserRole.USER);
//        assertThat(hongUser.getWithdrawReqAt()).isNull();
//        assertThat(hongUser.getLastLoginAt()).isEqualTo(hongUser.getCreatedAt());
//
//        assertThat(hongInfo.getEmail()).isNotNull();
//        assertThat(hongInfo.getProviderType()).isInstanceOf(ProviderType.class);
//        assertThat(hongInfo.getUsername()).isNotNull();
//
//        assertThat(hongUser.getUserKey()).isEqualTo(this.hongInfo.getUserKey());
//
//        entityManager.flush();
//    }
//
//    @Test
//    void duplicateEmailFail() {
//        UserInfo userinfo1 = createUserInfoRegisterRequest("dup@naver.com");
//        userInfoRepository.save(userinfo1);
//        entityManager.flush();
//
//        Assertions.assertThatThrownBy(()-> {
//            UserInfo userinfo2 = createUserInfoRegisterRequest("dup@naver.com");
//            userInfoRepository.save(userinfo2);
//            entityManager.flush();
//        }).isInstanceOf(Exception.class);
//
//    }
//
//
//}
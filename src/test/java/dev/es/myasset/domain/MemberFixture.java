package dev.es.myasset.domain;

import dev.es.myasset.application.provided.OAuthUserInfo;
import dev.es.myasset.domain.user.ProviderType;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;

import java.time.LocalDateTime;

public class MemberFixture {

    public static UserInfo createUserInfoRegisterRequest(String email) {
        return OAuthUserInfo.of("홍길동", ProviderType.GOOGLE, email);
    }

    // 정상적인 유저개인정보 엔티티 생성
    public static UserInfo createUserInfoRegisterRequest() {
        return createUserInfoRegisterRequest("hong@google.co.kr");
    }

    // 정상적인 유저 계정정보 엔티티 생성
    public static User createUserRegisterRequest() {
        return User.register(LocalDateTime.now());
    }

}

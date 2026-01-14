package dev.es.myasset.domain;

import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;

import java.time.LocalDateTime;

public class UserFixture {


    public static UserInfo createUserInfoRegisterRequest(String email) {
        return UserInfo.registerUserInfo("google", "12422", email, "홍길동");
    }


    // 정상적인 유저개인정보 엔티티 생성
    public static UserInfo createUserInfoRegisterRequest() {
        return UserInfo.registerUserInfo("google", "12422", "hong@gmail.com", "홍길동");
    }


}

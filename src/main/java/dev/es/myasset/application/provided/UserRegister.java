package dev.es.myasset.application.provided;

import dev.es.myasset.application.required.OAuthUserInfo;
import dev.es.myasset.domain.user.User;

// 소셜서버를 통해 회원정보를 받고 회원개인정보와 회원을 등록한다.
// interface -> kakao, google, naver -> OAuthUserInfo -> UserInfo & User
public interface UserRegister {

    User registerFromOAuth(OAuthUserInfo OAuthUserInfo);
}

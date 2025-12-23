package dev.es.myasset.application.provided;

import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.domain.user.User;

// 소셜서버를 통해 회원정보를 받고 회원개인정보와 회원을 등록한다.
public interface UserRegister {

    User registerFromOAuth(OAuth2UserInfo OAuthUserInfo);
}

package dev.es.myasset.application.provided;

import dev.es.myasset.domain.user.User;

// registerToken을 받고, 회원가입에 동의한 회원 등록 인터페이스
public interface UserRegister {

    User registerFromOAuth(String registerToken, boolean agreement);
}

package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.UserInfo;

/*
 * Jwt register Token(회원가입 토큰)을 userInfo 객체 파라미터로 변환시키는 인터페이스
 */
public interface RegisterTokenParser {
    UserInfo parse(String registerToken);
}

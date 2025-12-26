package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.UserInfo;

/*
 * 외부에서 받은 토큰을 유저개인정보 객체로 변환하는 인터페이스
 */
public interface UserInfoAssembler {
    UserInfo assembleUserInfo(String registerToken);

}

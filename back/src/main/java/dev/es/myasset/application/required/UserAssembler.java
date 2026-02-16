package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.UserInfo;

import java.time.LocalDateTime;


/*
 * 외부에서 받은 토큰에서 개인정보을 받아 User&UserInfo 객체를 생성하는 인터페이스
 */
public interface UserAssembler {

    UserInfo assembleUserInfo(String registerToken, LocalDateTime registerTime);

}

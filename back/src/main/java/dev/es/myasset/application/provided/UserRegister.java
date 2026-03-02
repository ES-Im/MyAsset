package dev.es.myasset.application.provided;

import dev.es.myasset.application.dto.OAuthSignupDto;
import dev.es.myasset.domain.user.UserInfo;

import java.time.LocalDateTime;

/*
 * 신규유저 외부 정보 조립하여 회원등록하는 인터페이스
 */
public interface UserRegister {

    UserInfo assembleUserInfo(OAuthSignupDto userInfo, LocalDateTime registerTime);

}

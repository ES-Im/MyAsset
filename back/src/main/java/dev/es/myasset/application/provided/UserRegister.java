package dev.es.myasset.application.provided;

import dev.es.myasset.application.dto.OAuthSignupDto;
import dev.es.myasset.domain.user.UserInfo;

import java.time.LocalDateTime;

/*
 * 외부에서 받은 신규유저 정보를 받아 회원등록하는 인터페이스
 */
public interface UserRegister {

    UserInfo assembleUserInfo(OAuthSignupDto userInfo, LocalDateTime registerTime);

}

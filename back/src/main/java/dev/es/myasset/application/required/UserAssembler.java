package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.UserInfo;

import java.time.LocalDateTime;
import java.util.Map;


/*
 * 외부에서 받은 토큰에서 개인정보을 받아 User&UserInfo 객체를 생성하는 인터페이스
 */
public interface UserAssembler {

    UserInfo assembleUserInfo(Object userInfo, LocalDateTime registerTime);

}


/* 현재 브런치 version01에서 진행 (기존 auth 코드 리팩토링 및 토큰 방향 수정, register token삭제 + 예외정리)
 * 2/20 이어서 할거 successHandler 부분이랑
 * 헥사고날이라서 register 부분을
 * oauthUserAssembler (adapter)
 * > userAssembler (application)
 * > userRegister (application)
 * > userService (application)와 같이 너무 복잡하게 했는데.
 * 리팩토링 방안 찾아보기
 */

/*
 * 그리고 핸들러부분 너무 무겁고, orElseGet부분이 위험해보임.
 * controller, authService 분할해서 책임 위임해야할 부분이 있으니 우선 로직을 완성한 뒤 나눌것.
 */
package dev.es.myasset.domain.sync;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SyncStatus {

    _20001(20001, org.springframework.http.HttpStatus.OK, "API 호출 및 회신 성공(정상)")
    , _20002(20002, org.springframework.http.HttpStatus.OK, "API 호출 및 회신 성공 (부하 개선을 위한 Timestamp 기능에 따라 회신 데이터가 없는 경우)")
    , _40001(40001, org.springframework.http.HttpStatus.BAD_REQUEST, "잘못된 요청입니다.")
    , _40002(40002, org.springframework.http.HttpStatus.BAD_REQUEST, "요청 파라미터에 문제가 있는 경우 (데이터타입, 길이 에러 등)")
    , _40003(40003, org.springframework.http.HttpStatus.BAD_REQUEST, "헤더값 미존재 또는 잘못된 헤더값")
    , _40101(40101, org.springframework.http.HttpStatus.UNAUTHORIZED, "유효하지 않은 접근토큰")
    , _40102(40102, HttpStatus.UNAUTHORIZED, "허용되지 않은 원격지 IP")
    , _40103(40103, HttpStatus.UNAUTHORIZED, "TLS 인증서 지문 검증 실패")
    , _40104(40104, HttpStatus.UNAUTHORIZED, "API 사용 권한 없음")
    , _40105(40105, HttpStatus.UNAUTHORIZED, "정보 조회 권한 없음")
    , _40106(40106, HttpStatus.UNAUTHORIZED, "전송요구 종료시점 경과")
    , _40107(40107, HttpStatus.UNAUTHORIZED, "철회된 전송요구")
    , _40108(40108, HttpStatus.UNAUTHORIZED, "접근토큰 갱신 필요")
    , _40301(40301, HttpStatus.FORBIDDEN, "올바르지 않은 API 호출")
    , _40302(40302, HttpStatus.FORBIDDEN, "일시적으로 클라이언트 요청 제한")
    , _40303(40303, HttpStatus.FORBIDDEN, "기관코드 확인 불가")
    , _40304(40304, HttpStatus.FORBIDDEN, "조회기간 오류")
    , _40305(40305, HttpStatus.FORBIDDEN, "조회 식별자 오류")
    , _40306(40306, HttpStatus.FORBIDDEN, "정기적 전송 불가")
    , _40401(40401, HttpStatus.NOT_FOUND, "요청한 엔드포인트 없음")
    , _40402(40402, HttpStatus.NOT_FOUND, "유효하지 않은 요청 목록")
    , _40403(40403, HttpStatus.NOT_FOUND, "정보주체(고객) 확인 불가")
    , _40501(40501, HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP Method")
    , _40801(40801, HttpStatus.REQUEST_TIMEOUT, "요청 처리 시간 초과")
    , _42901(42901, HttpStatus.PRECONDITION_REQUIRED, "요청 횟수 한도 초과")
    , _50001(50001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생")
    , _50005(50005, HttpStatus.INTERNAL_SERVER_ERROR, "정보전송자 서비스 불가(중계전문기관용)")
    , _50007(50007, HttpStatus.INTERNAL_SERVER_ERROR, "전문변환에러(중계전문기관용)")
    , _50008(50008, HttpStatus.INTERNAL_SERVER_ERROR, "시스템 정기점검")
    , _50301(50301, HttpStatus.SERVICE_UNAVAILABLE, "일시적인 서비스 불가");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    SyncStatus(Integer code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}

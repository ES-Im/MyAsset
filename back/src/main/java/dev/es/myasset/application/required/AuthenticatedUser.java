package dev.es.myasset.application.required;

/*
 * 현재의 SecurityContext에 인증정보의 UserKey를 조회
 */
public interface AuthenticatedUser {
    String getAuthenticatedUserKey();
    String getAuthenticatedUserRole();
}

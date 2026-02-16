package dev.es.myasset.application.required;

/*
 * 외부 소셜 서버에서 받은 사용자 정보
 */
public interface OAuth2UserInfo {
    String getProviderType();
    String getProviderId();
    String getEmail();
    String getUsername();
}

package dev.es.myasset.adapter.security.oauth.provider;

import dev.es.myasset.application.required.OAuth2UserInfo;

import java.util.Map;

public record KakaoOAuthUserInfo (
    Map<String, Object> attribute
) implements OAuth2UserInfo {
    @Override
    public String getProviderType() {
        return "KAKAO";
    }

    @Override
    public String getProviderId() {
        return this.attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return ((Map<String, Object>) this.attribute.get("kakao_account")).get("email").toString();
    }

    @Override
    public String getUsername() {
        return ((Map<String, Object>) this.attribute.get("properties")).get("nickname").toString();
    }
}

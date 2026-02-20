package dev.es.myasset.adapter.security.oauth.provider;

import dev.es.myasset.application.required.OAuth2UserInfo;

import java.util.Map;

public record NaverOAuthUserInfo(
    Map<String, Object> attribute
) implements OAuth2UserInfo {
    @Override
    public String getProviderType() {
        return "NAVER";
    }

    @Override
    public String getProviderId() {
        return ((Map<String, Object>) attribute.get("response")).get("id").toString();
    }

    @Override
    public String getEmail() {
        return ((Map<String, Object>) attribute.get("response")).get("email").toString();
    }

    @Override
    public String getUsername() {
        return ((Map<String, Object>) attribute.get("response")).get("name").toString();
    }
}

package dev.es.myasset.adapter.oauth;

import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.domain.shared.Email;
import dev.es.myasset.domain.user.ProviderType;

import java.util.Map;

public record NaverOAuthUserInfo(
    Map<String, Object> attribute
) implements OAuth2UserInfo {
    @Override
    public String getProvider() {
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
    public String getName() {
        return ((Map<String, Object>) attribute.get("response")).get("name").toString();
    }
}

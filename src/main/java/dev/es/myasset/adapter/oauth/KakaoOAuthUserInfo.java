package dev.es.myasset.adapter.oauth;

import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.domain.shared.Email;
import dev.es.myasset.domain.user.ProviderType;

import java.util.Map;

public record KakaoOAuthUserInfo (
    Map<String, Object> attribute
) implements OAuth2UserInfo {
    @Override
    public String getProvider() {
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
    public String getName() {
        return ((Map<String, Object>) this.attribute.get("properties")).get("nickname").toString();
    }
}

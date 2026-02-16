package dev.es.myasset.adapter.security.oauth;

import dev.es.myasset.application.required.OAuth2UserInfo;

import java.util.Map;

public record GoogleOAuthUserInfo(
    Map<String, Object> attribute
) implements OAuth2UserInfo {
    @Override
    public String getProviderType() {
        return "GOOGLE";
    }

    @Override
    public String getProviderId() {
        return this.attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return this.attribute.get("email").toString();
    }

    @Override
    public String getUsername() {
        return this.attribute.get("name").toString();
    }
}

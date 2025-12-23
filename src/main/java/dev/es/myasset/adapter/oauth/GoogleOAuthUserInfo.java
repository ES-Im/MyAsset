package dev.es.myasset.adapter.oauth;

import dev.es.myasset.application.required.OAuth2UserInfo;

import java.util.Map;

public record GoogleOAuthUserInfo(
    Map<String, Object> attribute
) implements OAuth2UserInfo {
    @Override
    public String getProvider() {
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
    public String getName() {
        return this.attribute.get("name").toString();
    }
}

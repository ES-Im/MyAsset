package dev.es.myasset.application.required;

public interface OAuth2UserInfo {
    String getProviderType();
    String getProviderId();
    String getEmail();
    String getUsername();
}

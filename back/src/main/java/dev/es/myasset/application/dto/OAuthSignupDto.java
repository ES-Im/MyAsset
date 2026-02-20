package dev.es.myasset.application.dto;


public record OAuthSignupDto(
        String providerType, String providerId, String email, String username
) {}

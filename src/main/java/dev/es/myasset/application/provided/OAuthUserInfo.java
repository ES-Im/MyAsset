package dev.es.myasset.application.provided;

import dev.es.myasset.domain.ProviderType;
import dev.es.myasset.domain.UserInfo;

import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.Objects.*;

// interface -> kakao, google, naver -> UserInfo DTO -> UserInfo & User
public record OAuthUserInfo(
        String userKey,
        String username,
        ProviderType providerType,
        String email
) {
    public static UserInfo of(String username,
                                           ProviderType providerType,
                                           String email) {

        validateEmail(email);

        return UserInfo.registerFromOAuth(
                UUID.randomUUID().toString(),
                requireNonNull(username),
                requireNonNull(providerType),
                requireNonNull(email)
        );
    }

    private static void validateEmail(String email) {
        Pattern EMAIL_PATTERN =
                Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        if(!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("invalid email");
        }
    }
}
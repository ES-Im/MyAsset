package dev.es.myasset.application.provided;

import dev.es.myasset.domain.user.ProviderType;
import dev.es.myasset.domain.user.UserInfo;

import static java.util.Objects.*;

// 소셜 서버로부터 전달받는 회원 정보 데이터를 매핑하기 위한 DTO
public record OAuthUserInfo (
        String username,
        ProviderType providerType,
        String email
) {
    public static UserInfo of(String username,
                           ProviderType providerType,
                           String email) {

        return UserInfo.registerFromOAuth(
                            requireNonNull(username),
                            requireNonNull(providerType),
                            requireNonNull(email)
        );
    }
}
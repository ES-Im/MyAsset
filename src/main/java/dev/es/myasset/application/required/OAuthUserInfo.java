package dev.es.myasset.application.required;

import dev.es.myasset.domain.user.ProviderType;
import dev.es.myasset.domain.user.UserInfo;

import static java.util.Objects.*;

// 소셜 서버로부터 전달받는 회원 정보 데이터를 매핑하기 위한 DTO
public record OAuthUserInfo (
        String username,
        ProviderType providerType,
        String email
) {
    public static UserInfo of(OAuthUserInfo oAuthUserInfo) {

        return UserInfo.registerFromOAuth(
                            requireNonNull(oAuthUserInfo.username),
                            requireNonNull(oAuthUserInfo.providerType),
                            requireNonNull(oAuthUserInfo.email)
        );
    }
}
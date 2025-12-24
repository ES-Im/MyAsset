package dev.es.myasset.adapter.security.oauth;

import dev.es.myasset.adapter.security.token.JwtTokenManager;
import dev.es.myasset.application.required.RegisterTokenParser;
import dev.es.myasset.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dev.es.myasset.domain.user.UserInfo.registerUserInfo;

@Component
@RequiredArgsConstructor
public class JwtRegisterTokenParser implements RegisterTokenParser {

    private final JwtTokenManager jwtTokenManager;

    @Override
    public UserInfo parse(String registerToken) {
        return registerUserInfo(
                jwtTokenManager.getProviderTypeFromRegisterToken(registerToken),
                jwtTokenManager.getProviderIdFromRegisterToken(registerToken),
                jwtTokenManager.getProviderEmailFromRegisterToken(registerToken),
                jwtTokenManager.getProviderUsernameFromRegisterToken(registerToken)
        );
    }

}

package dev.es.myasset.adapter.security.oauth;

import dev.es.myasset.adapter.security.auth.JwtTokenManagement;
import dev.es.myasset.application.required.UserInfoAssembler;
import dev.es.myasset.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.es.myasset.domain.user.UserInfo.registerUserInfo;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthUserInfoAssembler implements UserInfoAssembler {

    private final JwtTokenManagement jwtTokenManagement;

    @Override
    public UserInfo assembleUserInfo(String registerToken) {
        log.info("User Register token: {}", registerToken);

        Map<String, String> registerTokenMap = jwtTokenManagement.parseToken(registerToken);

        return registerUserInfo(
                registerTokenMap.get("providerType"),
                registerTokenMap.get("providerId"),
                registerTokenMap.get("email"),
                registerTokenMap.get("username")
        );
    }
}

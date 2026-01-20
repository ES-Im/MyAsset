package dev.es.myasset.adapter.security.oauth;

import dev.es.myasset.adapter.security.auth.JwtTokenManagement;
import dev.es.myasset.application.required.UserAssembler;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthUserAssembler implements UserAssembler {

    private final JwtTokenManagement jwtTokenManagement;

    @Override
    public UserInfo assembleUserInfo(String registerToken, LocalDateTime time) {
        log.info("User Register token: {}", registerToken);

        Map<String, String> registerTokenMap = jwtTokenManagement.parseToken(registerToken);
       
        return UserInfo.registerUserInfo(User.register(time)
                , registerTokenMap.get("providerType")
                , registerTokenMap.get("providerId")
                , registerTokenMap.get("email")
                , registerTokenMap.get("username")
        );
    }
}

package dev.es.myasset.adapter.security.oauth;

import dev.es.myasset.adapter.security.edited.AuthService;
import dev.es.myasset.application.required.OAuth2UserInfo;
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

    private final AuthService authService;

    @Override
    public UserInfo assembleUserInfo(Object provided, LocalDateTime registerTime) {
        log.info("UserInfo 조립 시작");

        User savedUser = User.register(registerTime);
        OAuth2UserInfo providedInfo = (OAuth2UserInfo) provided;

        UserInfo userInfo = UserInfo.registerUserInfo(
                providedInfo.getProviderType(),
                providedInfo.getProviderId(),
                providedInfo.getEmail(),
                providedInfo.getUsername()
        );

        userInfo.linkUser(savedUser);
        log.info("UserInfo 조립 완료 - user entity 링크 유무 : {}", savedUser.equals(userInfo.getUser()));

        return userInfo;
    }
}

package dev.es.myasset.application;

import dev.es.myasset.application.provided.UserRegister;
import dev.es.myasset.application.required.OAuth2UserInfo;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.es.myasset.domain.user.UserInfo.registerUserInfo;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService implements UserRegister {

    private final UserRepository userRepository;



    @Override
    public User registerFromOAuth(OAuth2UserInfo oAuthUserInfo) {

        LocalDateTime now =  LocalDateTime.now();
        UserInfo userInfo = registerUserInfo(oAuthUserInfo.getProviderType()
                                , oAuthUserInfo.getProviderId()
                                , oAuthUserInfo.getEmail()
                                , oAuthUserInfo.getUsername());

        log.info("userKey = {}",  userInfo.getUserKey());

        User user = User.register(now);
        user.linkUserInfo(userInfo);

        userRepository.save(user);
        log.info("user = {}",  user.getUserKey());

        return user;
    }
}

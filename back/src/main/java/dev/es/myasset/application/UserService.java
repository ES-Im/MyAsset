package dev.es.myasset.application;

import dev.es.myasset.application.dto.OAuthSignupDto;
import dev.es.myasset.application.exception.user.NonExistAccount;
import dev.es.myasset.application.provided.UserRegister;
import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserRegister {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserInfo assembleUserInfo(OAuthSignupDto providedUserInfo, LocalDateTime registerTime) {
        log.info("UserInfo 조립 시작");

        User savedUser = User.register(registerTime);

        UserInfo userInfo = UserInfo.registerUserInfo(
                providedUserInfo.providerType(),
                providedUserInfo.providerId(),
                providedUserInfo.email(),
                providedUserInfo.username()
        );

        userInfo.linkUser(savedUser);
        userInfoRepository.save(userInfo);

        log.info("UserService:UserInfo DB 등록 완료 - user&userInfo 링크 유무 : {}", savedUser.equals(userInfo.getUser()));

        return userInfo;
    }

    public void activateUser(String userKey, LocalDateTime requestTime) {
        User user = userRepository.findByUserKey(userKey).orElseThrow(() -> new NonExistAccount());

        try {
            user.requestActive(requestTime);

            userRepository.save(user);
        }catch (IllegalArgumentException e) {
            throw e;
        }
    }
}

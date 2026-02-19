package dev.es.myasset.application;

import dev.es.myasset.application.provided.UserRegister;
import dev.es.myasset.application.required.UserAssembler;
import dev.es.myasset.application.required.UserInfoRepository;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.application.exception.user.AgreementRequiredException;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserRegister {

    private final UserAssembler userAssembler;
    private final UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public UserInfo registerFromOAuth(Object providedUserInfo) {
        LocalDateTime now = LocalDateTime.now();

        UserInfo userInfo = userAssembler.assembleUserInfo(providedUserInfo, now);
        userInfoRepository.save(userInfo);

        log.info("회원등록이 성공하였습니다.");

        return userInfo;
    }

}

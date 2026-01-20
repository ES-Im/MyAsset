package dev.es.myasset.application;

import dev.es.myasset.application.provided.UserRegister;
import dev.es.myasset.application.required.UserAssembler;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.application.exception.user.AgreementRequiredException;
import dev.es.myasset.domain.user.User;
import dev.es.myasset.domain.user.UserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService implements UserRegister {

    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    @Override
    public User registerFromOAuth(String registerToken, boolean agreement) {
        LocalDateTime now = LocalDateTime.now();

        if(!agreement){
            log.info("회원등록이 실패하였습니다 - 가입미동의");
            throw new AgreementRequiredException();
        }

        UserInfo userInfo = userAssembler.assembleUserInfo(registerToken, now);

        log.info("userKey = {}",  userInfo.getUser());

        User savedUser = userRepository.save(userInfo.getUser());

        log.info("user = {}",  savedUser.getUserKey());

        log.info("회원등록이 성공하였습니다.");

        return savedUser;
    }

}

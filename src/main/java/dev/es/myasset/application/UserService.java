package dev.es.myasset.application;

import dev.es.myasset.application.provided.UserRegister;
import dev.es.myasset.application.required.UserInfoAssembler;
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
    private final UserInfoAssembler userInfoAssembler;

    @Override
    public User registerFromOAuth(String registerToken, boolean agreement) {
        if(!agreement){
            log.info("회원등록이 실패하였습니다 - 가입미동의");
            throw new AgreementRequiredException();
        }

        LocalDateTime now =  LocalDateTime.now();
        User user = User.register(now);

        UserInfo userInfo = userInfoAssembler.assembleUserInfo(registerToken);
        userInfo.linkUser(user);


        log.info("userKey = {}",  userInfo.getUserKey());

        userRepository.save(user);

        log.info("user = {}",  user.getUserKey());

        log.info("회원등록이 성공하였습니다.");

        return user;
    }


}

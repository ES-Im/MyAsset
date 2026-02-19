package dev.es.myasset.adapter.security.edited;

import dev.es.myasset.application.exception.oauth.ExpiredRefreshTokenException;
import dev.es.myasset.application.exception.oauth.InvalidRefreshTokenException;
import dev.es.myasset.application.required.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
 * Auth관련 유스케이스 관리
 * 로그인, 로그아웃, 토큰 재발행
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtCookieManager jwtCookieManager;
    private final RedisManager redisManager;
    private final UserRepository userRepository;

    @Transactional
    public String reIssueToken(String previousRefreshToken, HttpServletResponse response) {

        if(previousRefreshToken == null){
            throw new ExpiredRefreshTokenException();
        }

        String userKey = jwtTokenUtil.getSubject(previousRefreshToken);
        redisManager.validateRefreshToken(userKey, previousRefreshToken);

        String userRole = userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new InvalidRefreshTokenException())
                .getRole().name();

        String newAccessToken = jwtTokenUtil.generateAccessToken(userKey, userRole);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(userKey);

        jwtCookieManager.setRefreshCookie(newRefreshToken, response);
        redisManager.saveRefreshToken(userKey, newRefreshToken);

        return newAccessToken;
    }

}

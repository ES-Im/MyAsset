package dev.es.myasset.adapter.security.token;

import dev.es.myasset.adapter.security.redis.RedisManager;
import dev.es.myasset.application.UserService;
import dev.es.myasset.application.exception.oauth.ExpiredRefreshTokenException;
import dev.es.myasset.application.exception.oauth.InvalidRefreshTokenException;
import dev.es.myasset.application.exception.oauth.InvalidTokenException;
import dev.es.myasset.application.exception.oauth.MissingTokenException;
import dev.es.myasset.application.required.UserRepository;
import dev.es.myasset.domain.user.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;


/*
 * Auth Token관련
 * 로그인, 로그아웃, 토큰 재발행
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtCookieManager jwtCookieManager;
    private final RedisManager redisManager;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public String reIssueToken(String previousRefreshToken, HttpServletResponse response) {

        if(previousRefreshToken == null){
            throw new ExpiredRefreshTokenException();
        }

        String userKey = jwtTokenUtil.getSubject(previousRefreshToken);
        redisManager.validateRefreshToken(userKey, previousRefreshToken);

        User user = userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new InvalidRefreshTokenException());

        String newAccessToken = jwtTokenUtil.generateAccessToken(userKey, user.getRole().name());
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(userKey);

        jwtCookieManager.setRefreshCookie(newRefreshToken, response);
        redisManager.saveRefreshToken(userKey, newRefreshToken);

        return newAccessToken;
    }

    public void clearToken(String refreshToken, HttpServletResponse response) {
        jwtCookieManager.removeRefreshCookie(response);
        log.info("jwt 쿠키 정리 삭제 완료");
        String userKey = null;

        try {
            userKey = jwtTokenUtil.getSubject(refreshToken);
            if(userKey == null || userKey.isBlank()) return;
            redisManager.deleteRefreshToken(userKey);   // to-do redis에 남아있는 RT| 부분도 expiration TTL 걸어서 자동 정리되게끔 설정.
        } catch (Exception e) {
            log.error("clearToken 중 redis에서 해당 RefreshToken 삭제 실패 | error = {}, {}"
                    , e.getClass(), e.getMessage());
            return;
        }
    }

    public boolean activateUserByToken(String activateToken, Instant requestTime, HttpServletResponse response) {

        jwtCookieManager.removeActivateCookie(response);

        try {
            if (activateToken == null) throw new MissingTokenException();
            String userKey = jwtTokenUtil.getSubject(activateToken);

            if (userKey == null || userKey.isBlank()) throw new InvalidTokenException();

            userService.activateUser(userKey, requestTime);
        } catch (MissingTokenException e) {
            return false;
        } catch (InvalidTokenException e) {
            return false;
        }

        return true;
    }


}

package dev.es.myasset.adapter.security.token;

import dev.es.myasset.adapter.security.redis.RedisManager;
import dev.es.myasset.application.exception.oauth.*;
import dev.es.myasset.application.exception.redis.GlobalRedisException;
import dev.es.myasset.application.required.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    public void clearToken(String refreshToken, HttpServletResponse response) {
        jwtCookieManager.removeRefreshCookie(response);

        String userKey;

        try {
            userKey = jwtTokenUtil.getSubject(refreshToken);
        } catch (GlobalAuthException e) {
            return;
        }

        if(userKey == null || userKey.isBlank()) return;

        try {
            redisManager.deleteRefreshToken(userKey);   // to-do redis에 남아있는 RT| 부분도 expiration TTL 걸어서 자동 정리되게끔 설정.
        } catch (GlobalRedisException e) {
            return;
        }
    }

}

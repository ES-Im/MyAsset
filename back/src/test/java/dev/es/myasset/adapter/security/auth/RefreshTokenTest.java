package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.adapter.security.auth.support.SecurityTestSupport;
import dev.es.myasset.adapter.security.redis.RedisManager;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static dev.es.myasset.domain.user.UserRole.ROLE_USER;
import static dev.es.myasset.domain.user.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RefreshTokenTest extends SecurityTestSupport {


    @Autowired protected RedisManager redisManager;
    @Autowired protected RedisTemplate<Object, Object> redisTemplate;

    @Test
    @DisplayName("/api/auth/logout시 accessToken이 없어도 HttpStatus.OK 결과, refreshToken관련 redis와 cookie가 정리된다.")
    void odd_Value_in_accessToken_with_logout() throws Exception {
        String refreshToken = setToken("REFRESH", "user", 10_000_000);

        mockMvc.perform(post("/api/auth/logout")
                .cookie(new Cookie("refreshToken", refreshToken + "odd"))
        ).andExpect(status().isOk()).andExpect(content().string("success")
        ).andExpect(cookie().maxAge("refreshToken", 0)
        );

        assertThat(!existRefreshToken("user")).isTrue();
    }

    @Test
    @DisplayName("/api/auth/refresh 호출 시 인증정보(accessToken) 무관(jwtFilter x) HttpStatus.OK & 새로운 refreshToken관련 redis & 쿠키")
    void proper_freshToken_can_refresh_accessToken() throws Exception {
        registerUser("user", ACTIVE, ROLE_USER);

        String refreshToken = setToken("REFRESH", "user", 100_000);
        redisManager.saveRefreshToken("user", refreshToken);

        mockMvc.perform(post("/api/auth/refresh")
                .cookie(new Cookie("refreshToken", refreshToken))
        ).andExpect(status().isOk()
        ).andExpect(cookie().exists("refreshToken")
        );

        assertThat(existRefreshToken("user")).isTrue();
    }

    @Test
    @DisplayName("refreshToken이 위조되면 refresh가 실패하며, 쿠키와 redis에 저장되지 않는다.")
    void odd_freshToken_cant_refresh_accessToken() throws Exception {
        registerUser("expiredUser", ACTIVE, ROLE_USER);

        String refreshToken = setToken("REFRESH", "expiredUser", 100_000);
        redisManager.saveRefreshToken("expiredUser", refreshToken);

        mockMvc.perform(post("/api/auth/refresh")
                .cookie(new Cookie("refreshToken", refreshToken + "odd"))
        ).andExpect(status().isUnauthorized()
        ).andExpect(cookie().doesNotExist("refreshToken"));

        assertThat(existRefreshToken("expiredUser")).isTrue();
    }

    @Test
    @DisplayName("refreshToken이 없다면 refresh가 실패하며, 쿠키와 redis에 저장되지 않는다.")
    void none_freshToken_cant_refresh_accessToken() throws Exception {
        registerUser("expiredUser", ACTIVE, ROLE_USER);

        String refreshToken = setToken("REFRESH", "expiredUser", 100_000);
        redisManager.saveRefreshToken("expiredUser", refreshToken);

        mockMvc.perform(post("/api/auth/refresh")
        ).andExpect(status().isUnauthorized()
        ).andExpect(cookie().doesNotExist("refreshToken"));

        assertThat(existRefreshToken("expiredUser")).isTrue();
    }

    @Test
    @DisplayName("refreshToken이 만료됬다면 refresh가 실패하며,쿠키와 redis에 저장되지 않는다.")
    void expired_freshToken_cant_refresh_accessToken() throws Exception {
        registerUser("expiredUser", ACTIVE, ROLE_USER);

        String refreshToken = setToken("REFRESH", "expiredUser", -5);
        redisManager.saveRefreshToken("expiredUser", refreshToken);

        mockMvc.perform(post("/api/auth/refresh")
        ).andExpect(status().isUnauthorized()
        ).andExpect(cookie().doesNotExist("refreshToken"));

        assertThat(existRefreshToken("expiredUser")).isTrue();
    }

    private boolean existRefreshToken(String refreshToken) {
        return redisTemplate.hasKey("RT|" + refreshToken);
    }

}

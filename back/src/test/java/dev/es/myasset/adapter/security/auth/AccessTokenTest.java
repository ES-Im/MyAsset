package dev.es.myasset.adapter.security.auth;

import dev.es.myasset.adapter.security.auth.support.SecurityTestSupport;
import dev.es.myasset.domain.user.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static dev.es.myasset.domain.user.UserRole.ROLE_ADMIN;
import static dev.es.myasset.domain.user.UserRole.ROLE_USER;
import static dev.es.myasset.domain.user.UserStatus.ACTIVE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccessTokenTest extends SecurityTestSupport {

    @DisplayName("유저 상태별 api 접근불가 테스트")
    @ParameterizedTest(name= "{index} > Status: ``{0}``상태의 USER는 /api/user/** 접근 불가(403)")
    @CsvSource(
            {"DORMANT", "WITHDRAWN", "WITHDRAW_PENDING"}
    )
    void dormant_user_cant_access_user_api(UserStatus status) throws Exception {
        registerUser("dormantUser", status, ROLE_USER);

        mockMvc.perform(get("/api/user/")
                .header("Authorization", bearer("dormantUser", "ROLE_USER"))
        ).andExpect(status().isForbidden());

    }

    @Test
    @DisplayName("Active 유저 api접근 허용 테스트")
    void active_user_can_access_user_api() throws Exception {
        registerUser("activeUser", ACTIVE, ROLE_USER);

        mockMvc.perform(get("/api/user/")
                        .header("Authorization", bearer("activeUser", "ROLE_USER"))
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("정상적인 accessToken을 가지면 유저 api접근 허용")
    void accessToken_user_cant_access_user_api() throws Exception {

        registerUser("AccessTokenUser", ACTIVE, ROLE_USER);

        String accessToken = "Bearer " + setToken("ACCESS","AccessTokenUser", 1_000_000);

        mockMvc.perform(get("/api/user/")
                .header("Authorization", accessToken)
        ).andExpect(status().isOk());
    }


    @Test
    @DisplayName("accessToken이 없으면 oauth2 login페이지로 이동")
    void non_accessToken_user_cant_access_user_api() throws Exception {
        registerUser("nonAccessTokenUser", ACTIVE, ROLE_USER);

        mockMvc.perform(get("/api/user/"))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("accessToken이 만료되면 401오류(refresh는 프론트에서 호출)")
    void expired_accessToken_user_cant_access_user_api() throws Exception {

        registerUser("expiredAccessTokenUser", ACTIVE, ROLE_USER);

        String accessToken = "Bearer " + setToken("ACCESS","expiredAccessTokenUser", -5);

        mockMvc.perform(get("/api/user/")
                .header("Authorization", accessToken)
        ).andExpect(status().isUnauthorized());
    }
    
    @Test
    @DisplayName("accessToken가 위조되면 /login 으로 이동")
    void forgery_accessToken_user_cant_access_user_api() throws Exception {
        registerUser("forgeryAccessTokenUser", ACTIVE, ROLE_USER);
        String accessToken = "Bearer " + setToken("ACCESS","forgeryAccessTokenUser", 1000);
        String forgeryAccessToken = accessToken.substring(0, 1) + "_" + accessToken.substring(2);

        mockMvc.perform(get("/api/user/")
                .header("Authorization", forgeryAccessToken)
        ).andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    @DisplayName("USER의 accessToken으로 접속시, admin api 접근 불가")
    void accessToken_user_cant_access_admin_url() throws  Exception {
        registerUser("accessTokenUser", ACTIVE, ROLE_USER);
        String accessToken = "Bearer " + setToken("ACCESS","accessTokenUser", 1000);

        mockMvc.perform(get("/api/admin/")
                .header("Authorization", accessToken)
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Admin의 accessToken으로 접속시, admin api 접근 가능")
    void accessToken_admin_cant_access_user_url() throws  Exception {
        registerUser("accessTokenUser", ACTIVE, ROLE_ADMIN);
        String accessToken = "Bearer " + setToken("ACCESS", "accessTokenUser", 1000);

        mockMvc.perform(get("/api/admin/")
                .header("Authorization", accessToken)
        ).andExpect(status().isOk());
    }


}

// restDocs 쿠키Docs관련 - https://docs.spring.io/spring-restdocs/docs/current/api/org/springframework/restdocs/cookies/CookieDocumentation.html

package dev.es.myasset.adapter.docs.security;

import dev.es.myasset.adapter.docs.RestDocsSupport;
import dev.es.myasset.adapter.security.token.TokenService;
import dev.es.myasset.adapter.webapi.auth.OAuthRestController;
import dev.es.myasset.application.exception.oauth.InvalidRefreshTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OAuthRestControllerDocsTest extends RestDocsSupport {

    private final TokenService tokenService = mock(TokenService.class);

    @Override
    protected Object initController() {
        return new OAuthRestController(tokenService);
    }

    @Test
    @DisplayName("ReIssue 성공 : Access_token 응답 + Refresh_Token 쿠키 설정")
    void success_issueToken() throws Exception {
        String previousRefreshToken = "dummy token";
        String newReFreshToken = "dummy token";
        String newAccessToken = "access token";

        Mockito.when(tokenService.reIssueToken(anyString(), any(HttpServletResponse.class)))
                .thenAnswer(c -> {
                    Cookie cookie = new Cookie("refreshToken", newReFreshToken);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);

                    HttpServletResponse res = c.getArgument(1);
                    res.addCookie(cookie);

                    return newAccessToken;
                });

        mockMvc.perform(post("/api/auth/refresh")
                    .cookie(new Cookie("refreshToken", previousRefreshToken)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(content().string(newAccessToken))


                .andDo(document("reIssue",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        responseBody(),

                        requestCookies(
                            cookieWithName("refreshToken").description("이전의 refresh_token").optional()
                        ),


                        responseCookies(
                            cookieWithName("refreshToken").description("새로 발급된 refresh_token")
                        )

                    )
                );
    }

    @Test
    @DisplayName("ReIssue 실패 : INVALID_REFRESH_TOKEN")
    void fail_issueToken_INVALID_REFRESH_TOKEN() throws Exception {
        String invalidRefreshToken = "dummy token";

        Mockito.when(tokenService.reIssueToken(anyString(), any(HttpServletResponse.class)))
                .thenThrow(new InvalidRefreshTokenException());


        mockMvc.perform(post("/api/auth/refresh")
                .cookie(new Cookie("refreshToken", invalidRefreshToken)))
                .andExpect(status().isUnauthorized())
                .andExpect(cookie().doesNotExist("refreshToken"))
                .andDo(
                        document("reIssue-invalid-refresh",

                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestCookies(
                                cookieWithName("refreshToken").description("유효하지 않은 refresh_toke")
                        ),

                        responseFields(
                            fieldWithPath("name").description("에러코드"),
                            fieldWithPath("httpStatus").description("HTTP 상태"),
                            fieldWithPath("message").description("에러 메세지")
                        )

                    )

                );
    }

    @Test
    @DisplayName("Logout 성공")
    void success_logout() throws Exception {
        String refreshToken = "dummy token";

        mockMvc.perform(post("/api/auth/logout")
                .cookie(new Cookie("refreshToken", refreshToken)))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist("refreshToken"))
                .andDo(
                        document("logout",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestCookies(
                                cookieWithName("refreshToken").description("정리 대상 토큰").optional()
                        ),
                        responseBody()
                    )
                );
    }

    @Test
    @DisplayName("Activate User 성공")
    void success_activateUser() throws Exception {
        String activateToken = "dummy token";

        Mockito.when(tokenService.activateUserByToken(anyString(), any(Instant.class), any(HttpServletResponse.class))
        ).thenReturn(true);

        mockMvc.perform(post("/api/auth/activateUser")
                .cookie(new Cookie("activateToken", activateToken)))
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(
                        document("activate_user_success",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),

                                requestCookies(
                                        cookieWithName("activateToken").description("계정 활성화에 사용되는 토큰")
                                ),

                                responseBody()
                        )
                );

    }

    @Test
    @DisplayName("Activate User 실패")
    void fail1_activateUser() throws Exception {
        String activateToken = "dummy token";

        Mockito.when(tokenService.activateUserByToken(anyString(), any(Instant.class), any(HttpServletResponse.class))
        ).thenReturn(false);

        mockMvc.perform(post("/api/auth/activateUser")
                .cookie(new Cookie("activateToken", activateToken)))
                .andExpect(status().isOk())
                .andExpect(content().string("fail"))
                .andDo(
                        document("activate_user_fail",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),

                                requestCookies(
                                        cookieWithName("activateToken").description("계정 활성화에 사용되는 토큰")
                                ),

                                responseBody()
                        )
                );

    }



}

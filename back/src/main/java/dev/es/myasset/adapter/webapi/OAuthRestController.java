package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.token.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthRestController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<Object> issueToken(@CookieValue(required = false) String refreshToken,
                                             HttpServletResponse response) {
        String accessToken = tokenService.reIssueToken(refreshToken, response);

        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@CookieValue(name="refreshToken", required = false) String refreshToken,
                                         HttpServletResponse response) {
        tokenService.clearToken(refreshToken, response);

        return ResponseEntity.ok("로그아웃 성공");
    }

}

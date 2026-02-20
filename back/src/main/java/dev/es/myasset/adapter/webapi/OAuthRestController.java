package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.token.TokenIssuer;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthRestController {

    private final TokenIssuer tokenIssuer;

    @PostMapping("/refresh")
    public ResponseEntity<Object> issueToken(@CookieValue(required = false) String refreshToken,
                                             HttpServletResponse response) {
        String accessToken = tokenIssuer.reIssueToken(refreshToken, response);
        return ResponseEntity.ok(accessToken);
    }

}

package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.token.TokenIssuer;
import dev.es.myasset.application.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final TokenIssuer tokenIssuer;


//    @GetMapping("/kakao")


    @PostMapping("/reissue")
    public ResponseEntity<Object> issueToken(@CookieValue(required = false) String refreshToken,
                                             HttpServletResponse response) {
        tokenIssuer.reIssueToken(refreshToken, response);
        return ResponseEntity.ok().build();
    }
}

package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.edited.AuthService;
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
    private final AuthService authService;


//    @GetMapping("/kakao")


    @PostMapping("/reissue")
    public ResponseEntity<Object> issueToken(@CookieValue(required = false) String refreshToken,
                                             HttpServletResponse response) {
        authService.reIssueToken(refreshToken, response);
        return ResponseEntity.ok().build();
    }
}

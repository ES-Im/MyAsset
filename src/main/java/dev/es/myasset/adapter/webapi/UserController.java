package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.auth.JwtTokenManagement;
import dev.es.myasset.application.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;
    private JwtTokenManagement jwtTokenManagement;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@CookieValue String registerToken,
                                               @Validated UserRequestCategory agreement) {

        userService.registerFromOAuth(registerToken, agreement.registerAgreement());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/re-issue")
    public ResponseEntity<Object> issueToken(@CookieValue(required = false) String refreshToken,
                                             HttpServletResponse response) {
        jwtTokenManagement.reIssueToken(refreshToken, response);
        return ResponseEntity.ok().build();
    }
}

package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.auth.JwtTokenManagement;
import dev.es.myasset.application.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final JwtTokenManagement jwtTokenManagement;



    @PostMapping("/onboarding/success")
    public ResponseEntity<Object> onboardingSuccess(@CookieValue("register_token") String registerToken,
                                                    @Validated @ModelAttribute UserRegisterRequest agreement,
                                                    HttpServletResponse response) throws IOException {

        log.info("onboarding 처리");

        userService.registerFromOAuth(registerToken, agreement.registerAgreement());

        log.info("onboarding 성공 registerToken={}", registerToken);
        response.sendRedirect("/dashboard");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/re-issue")
    public ResponseEntity<Object> issueToken(@CookieValue(required = false) String refreshToken,
                                             HttpServletResponse response) {
        jwtTokenManagement.reIssueToken(refreshToken, response);
        return ResponseEntity.ok().build();
    }
}

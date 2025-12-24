package dev.es.myasset.adapter.webapi;

import dev.es.myasset.application.UserService;
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

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@CookieValue String registerToken,
                                               @Validated UserRequestCategory agreement) {

        userService.registerFromOAuth(registerToken, agreement.registerAgreement());

        return ResponseEntity.ok().build();
    }


}

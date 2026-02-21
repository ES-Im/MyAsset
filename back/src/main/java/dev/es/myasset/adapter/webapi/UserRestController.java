package dev.es.myasset.adapter.webapi;

import dev.es.myasset.adapter.security.token.TokenService;
import dev.es.myasset.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final TokenService tokenService;


//    @GetMapping("/kakao")



}

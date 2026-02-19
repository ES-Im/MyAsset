package dev.es.myasset.adapter.webapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/base")
    public String base() {
        return "asset/base";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "asset/dashboard";
    }
}

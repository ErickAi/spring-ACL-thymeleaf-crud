package com.er.acl.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SecurityController {

    @GetMapping("/login")
    public String getAll() {
        log.info("GET: /login");
        return "login";
    }
}

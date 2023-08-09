package com.wanted.preonboarding.member.controller.testcontroller;

import com.wanted.preonboarding.member.annotation.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("/api/no-auth")
    public void noAuth() {
    }

    @Authentication
    @GetMapping("/api/authentication")
    public void authentication() {
    }
}

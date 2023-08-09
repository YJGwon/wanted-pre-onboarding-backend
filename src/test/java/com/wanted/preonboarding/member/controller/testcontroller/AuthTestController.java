package com.wanted.preonboarding.member.controller.testcontroller;

import com.wanted.preonboarding.member.annotation.Authentication;
import com.wanted.preonboarding.member.annotation.AuthenticationPrincipal;
import java.util.Map;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/api/resolve-principal")
    public ResponseEntity<Map<String, Long>> resolvePrincipal(@AuthenticationPrincipal final Long memberId) {
        return ResponseEntity.ok(Map.of("memberId", memberId));
    }
}

package com.hrowlxb.passy_backend.auth.controller;

import com.hrowlxb.passy_backend.global.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/user")
    public String test(@AuthenticationPrincipal SecurityUser user) {
        return "로그인한 사용자 : " + user.getEmail() + " (role: " + user.getRole() + ")";
    }
}
package com.hrowlxb.passy_backend.auth.controller;

import com.hrowlxb.passy_backend.auth.dto.LoginRequest;
import com.hrowlxb.passy_backend.auth.dto.MyInfoResponse;
import com.hrowlxb.passy_backend.auth.dto.SignUpRequest;
import com.hrowlxb.passy_backend.auth.dto.UpdateMyInfoRequest;
import com.hrowlxb.passy_backend.auth.service.AuthService;
import com.hrowlxb.passy_backend.global.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<MyInfoResponse> getMyInfo(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(authService.getMyInfo(user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal SecurityUser user) {
        authService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMyInfo(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody UpdateMyInfoRequest request) {
        authService.updateMyInfo(user, request);
        return ResponseEntity.ok().build();
    }
}

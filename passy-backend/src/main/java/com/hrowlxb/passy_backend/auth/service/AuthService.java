package com.hrowlxb.passy_backend.auth.service;

import com.hrowlxb.passy_backend.auth.domain.Role;
import com.hrowlxb.passy_backend.auth.domain.User;
import com.hrowlxb.passy_backend.auth.dto.LoginRequest;
import com.hrowlxb.passy_backend.auth.dto.MyInfoResponse;
import com.hrowlxb.passy_backend.auth.dto.SignUpRequest;
import com.hrowlxb.passy_backend.auth.dto.UpdateMyInfoRequest;
import com.hrowlxb.passy_backend.auth.repository.UserRepository;
import com.hrowlxb.passy_backend.config.JwtProvider;
import com.hrowlxb.passy_backend.global.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(
                request.email(),
                encodedPassword,
                request.nickname(),
                Role.USER);
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return jwtProvider.createToken(user.getEmail(), user.getRole());
    }

    public MyInfoResponse getMyInfo(SecurityUser user) {
        return new MyInfoResponse(user.getEmail(), user.getNickname());
    }

    @Transactional
    public void deleteUser(SecurityUser user) {
        userRepository.deleteByEmail(user.getEmail());
    }

    public void updateMyInfo(SecurityUser securityUser, UpdateMyInfoRequest request) {
        User user = userRepository.findByEmail(securityUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.setNickname(request.nickname());

        if (request.newPassword() != null && !request.newPassword().isBlank()) {
            if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
                throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
            }

            user.setPassword(passwordEncoder.encode(request.newPassword()));
        }

        userRepository.save(user);
    }
}

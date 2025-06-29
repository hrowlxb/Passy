package com.hrowlxb.passy_backend.auth.dto;

public record UpdateMyInfoRequest(
        String nickname,
        String currentPassword,
        String newPassword
) {
}

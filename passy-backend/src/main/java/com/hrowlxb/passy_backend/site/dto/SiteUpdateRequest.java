package com.hrowlxb.passy_backend.site.dto;

public record SiteUpdateRequest(
        String siteName,
        String loginId,
        String loginPw
) {
}

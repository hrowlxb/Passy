package com.hrowlxb.passy_backend.site.dto;

public record SiteResponse (
    String id,
    String siteName,
    String loginId,
    String loginPw
) {}
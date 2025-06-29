package com.hrowlxb.passy_backend.site.dto;

import jakarta.validation.constraints.NotBlank;

public record SiteSaveRequest (

        @NotBlank String siteName,
        @NotBlank String loginId,
        @NotBlank String loginPw
) {}
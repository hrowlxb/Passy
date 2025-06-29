package com.hrowlxb.passy_backend.site.controller;

import com.hrowlxb.passy_backend.global.SecurityUser;
import com.hrowlxb.passy_backend.site.dto.SiteSaveRequest;
import com.hrowlxb.passy_backend.site.dto.SiteResponse;
import com.hrowlxb.passy_backend.site.dto.SiteUpdateRequest;
import com.hrowlxb.passy_backend.site.service.SiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/site")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @PostMapping
    public ResponseEntity<Void> save(@AuthenticationPrincipal SecurityUser user,
                                     @Valid @RequestBody SiteSaveRequest request) {
        siteService.saveSite(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SiteResponse>> getAll(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(siteService.getSites(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSite(
            @PathVariable String id,
            @RequestBody SiteUpdateRequest request,
            @AuthenticationPrincipal SecurityUser user
    ) {
        siteService.updateSite(user, id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(
            @PathVariable String id,
            @AuthenticationPrincipal SecurityUser user
    ) {
        siteService.deleteSite(user, id);
        return ResponseEntity.noContent()   .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponse> getOne(
            @PathVariable String id,
            @AuthenticationPrincipal SecurityUser user
    ) {
        return ResponseEntity.ok(siteService.getSite(user, id));
    }
}
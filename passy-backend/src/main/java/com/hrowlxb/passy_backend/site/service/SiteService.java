package com.hrowlxb.passy_backend.site.service;

import com.hrowlxb.passy_backend.global.SecurityUser;
import com.hrowlxb.passy_backend.global.util.AesEncryptor;
import com.hrowlxb.passy_backend.site.dto.SiteSaveRequest;
import com.hrowlxb.passy_backend.site.domain.SavedSite;
import com.hrowlxb.passy_backend.site.dto.SiteResponse;
import com.hrowlxb.passy_backend.site.dto.SiteUpdateRequest;
import com.hrowlxb.passy_backend.site.repository.SavedSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SavedSiteRepository savedSiteRepository;
    private final AesEncryptor aesEncryptor;

    public void saveSite(SecurityUser user, SiteSaveRequest request) {
        SavedSite site = SavedSite.builder()
                .email(user.getEmail())
                .siteName(request.siteName())
                .loginId(request.loginId())
                .loginPw(aesEncryptor.encrypt(request.loginPw()))
                .build();

        savedSiteRepository.save(site);
    }

    public List<SiteResponse> getSites(SecurityUser user) {
        return savedSiteRepository.findAllByEmail(user.getEmail()).stream()
                .map(site -> new SiteResponse(
                        site.getId(),
                        site.getSiteName(),
                        site.getLoginId(),
                        aesEncryptor.decrypt(site.getLoginPw())
                ))
                .toList();
    }

    public void updateSite(SecurityUser user, String id, SiteUpdateRequest request) {
        SavedSite site = savedSiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사이트 정보가 없습니다."));

        if (!site.getEmail().equals(user.getEmail())) {
            throw new SecurityException("권한이 없습니다.");
        }

        site.setSiteName(request.siteName());
        site.setLoginId(request.loginId());
        site.setLoginPw(aesEncryptor.encrypt(request.loginPw()));

        savedSiteRepository.save(site);
    }

    public void deleteSite(SecurityUser user, String id) {
        SavedSite site = savedSiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사이트 정보가 없습니다."));

        if (!site.getEmail().equals(user.getEmail())) {
            throw new SecurityException("권한이 없습니다.");
        }

        savedSiteRepository.deleteById(id);
    }

    public SiteResponse getSite(SecurityUser user, String id) {
        SavedSite site = savedSiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사이트 정보가 없습니다."));

        if (!site.getEmail().equals(user.getEmail())) {
            throw new SecurityException("권한이 없습니다.");
        }

        return new SiteResponse(
                site.getId(),
                site.getSiteName(),
                site.getLoginId(),
                aesEncryptor.decrypt(site.getLoginPw())
        );
    }
}

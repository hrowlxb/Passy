package com.hrowlxb.passy_backend.ocr.controller;

import com.hrowlxb.passy_backend.global.SecurityUser;
import com.hrowlxb.passy_backend.ocr.service.OcrService;
import com.hrowlxb.passy_backend.site.dto.SiteSaveRequest;
import com.hrowlxb.passy_backend.site.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OcrController {

    private final OcrService ocrService;
    private final SiteService siteService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) {
        String result = ocrService.extractText(image);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> extractAndSave(@RequestParam("image") MultipartFile image,
                                               @AuthenticationPrincipal SecurityUser user) throws Exception {

        String extractedText = ocrService.extractText(image);

        String loginId = extractField(extractedText, "아이디");
        String loginPw = extractField(extractedText, "비밀번호");
        String siteName = "OCR로 저장된 사이트";

        SiteSaveRequest request = new SiteSaveRequest(siteName, loginId, loginPw);
        siteService.saveSite(user, request);

        return ResponseEntity.ok().build();
    }

    private String extractField(String text, String key) {
        return Arrays.stream(text.split("\n"))
                .filter(line -> line.contains(key))
                .map(line -> line.split(":", 2))
                .filter(parts -> parts.length == 2)
                .map(parts -> parts[1].trim())
                .findFirst()
                .orElse("");
    }
}

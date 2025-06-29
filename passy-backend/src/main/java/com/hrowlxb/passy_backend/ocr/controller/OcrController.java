package com.hrowlxb.passy_backend.ocr.controller;

import com.hrowlxb.passy_backend.ocr.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OcrController {

    private final OcrService ocrService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) {
        String result = ocrService.extractText(image);
        return ResponseEntity.ok(result);
    }
}

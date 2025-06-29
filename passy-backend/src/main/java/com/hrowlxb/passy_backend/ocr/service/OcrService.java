package com.hrowlxb.passy_backend.ocr.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
@RequiredArgsConstructor
public class OcrService {

    private final Tesseract tesseract;

    public String extractText(MultipartFile image) {
        try {

            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

            File tempFile = File.createTempFile("ocr_", "jpg");
            ImageIO.write(bufferedImage, "jpg", tempFile);

            String result = tesseract.doOCR(tempFile);

            tempFile.delete();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("OCR 처리 실패" ,e);
        }
    }
}

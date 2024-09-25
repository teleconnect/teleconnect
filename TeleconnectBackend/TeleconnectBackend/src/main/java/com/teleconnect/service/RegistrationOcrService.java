package com.teleconnect.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class RegistrationOcrService {

    private final ITesseract tesseract;

    public RegistrationOcrService() {
        tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\e031950\\Desktop\\Project_Work\\TeleconnectBackend\\src\\main\\resources\\Tess4J\\tessdata");
        tesseract.setLanguage("eng");
    }

    public String extractAadhaarNumberFromImage(File aadharImage) throws TesseractException {
        String result = tesseract.doOCR(aadharImage);
        return extractAadharNumber(result);
    }

    public boolean isAadhaarImage(File aadharImage) {
        try {
            String result = tesseract.doOCR(aadharImage);
            return extractAadharNumber(result) != null;
        } catch (TesseractException e) {
            return false;
        }
    }

    public String extractAadharNumber(String ocrText) {
        String regex = "\\b\\d{4}\\s?\\d{4}\\s?\\d{4}\\b";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(ocrText);

        if (matcher.find()) {
            return matcher.group().replaceAll("\\s", "");
        }

        return null;
    }
}

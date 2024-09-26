package com.teleconnect.service;
 
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
 
import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
 
@Service
public class RegistrationOcrService {
 
    private final Tesseract tesseract;
 
    public RegistrationOcrService() {
        tesseract = new Tesseract();
 
        // Get the path to the tessdata directory in the resources folder
        String resourcePath;
        try {
            // Retrieve the URL of the tessdata folder and decode it
            resourcePath = URLDecoder.decode(
                RegistrationOcrService.class.getClassLoader().getResource("Tess4J/tessdata").getPath(),
                StandardCharsets.UTF_8.toString()
            );
 
            // Set the Tesseract data path
            tesseract.setDatapath(new File(resourcePath).getAbsolutePath()); // Use the absolute path directly
        } catch (Exception e) {
            // Log and handle any exceptions that occur while retrieving the path
            e.printStackTrace();
            throw new RuntimeException("Failed to load Tesseract data path", e);
        }
 
        // Set the language for Tesseract
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

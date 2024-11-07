package com.example.wavespringboot.service;

public interface QRCodeGenerator {
    String generateQRCodeImage(String text, int width, int height, String filePath);
    String getQRCodeImageBase64(String text, int width, int height);
}

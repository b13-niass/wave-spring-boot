package com.example.wavespringboot.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface MessageService {
    boolean sendMessage(String recipient, String message);
    String sendMailWithThymeleafAndQRCode(String recipient, String message, String qrCodeData) throws MessagingException, UnsupportedEncodingException ;
    }
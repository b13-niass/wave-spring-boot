package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.service.MessageService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;

@Service
@Qualifier("smsService")
public class SmsService implements MessageService {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;

    public SmsService(@Value("${infobip.api.url}") String apiUrl,
                      @Value("${infobip.api.key}") String apiKey,
                      RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean sendMessage(String recipient, String message) {
        String url = apiUrl + "/sms/1/text/single";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "App " + apiKey);

        String payload = String.format("{\"to\":\"%s\", \"text\":\"%s\"}", recipient, message);
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public String sendMailWithThymeleafAndQRCode(String recipient, String message, String qrCodeData) throws MessagingException, UnsupportedEncodingException {
        return "";
    }
}

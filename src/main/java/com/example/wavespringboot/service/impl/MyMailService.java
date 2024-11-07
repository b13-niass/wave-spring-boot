package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.service.MessageService;
import com.example.wavespringboot.service.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


@Service
@Qualifier("mailService")
public class MyMailService implements MessageService {

    private final JavaMailSender mailSender;
    private final QRCodeGenerator qrCodeGenerator;
    private final ITemplateEngine templateEngine;


    public MyMailService(ITemplateEngine templateEngine,JavaMailSender mailSender, QRCodeGenerator qrCodeGenerator) {
        this.mailSender = mailSender;
        this.qrCodeGenerator = qrCodeGenerator;
        this.templateEngine = templateEngine;
    }


    @Override
    public boolean sendMessage(String recipient, String message) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setTo(recipient);
            helper.setSubject("Notification");
            helper.setText(message, true);

            mailSender.send(mail);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String sendMailWithThymeleafAndQRCode(String recipient, String message, String qrCodeData) throws MessagingException, UnsupportedEncodingException {
        String qrCodeBase64 = this.qrCodeGenerator.getQRCodeImageBase64(qrCodeData, 200, 200);
        Context context = new Context();
        context.setVariable("qrCodeCid", "qrCodeImage");  // Set a unique content ID

        String process = templateEngine.process("carte.html", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("Welcome");
        helper.setText(process, true);
        helper.setTo(recipient);
        helper.setFrom("barhamadieng66@gmail.com", "Wave Mobile");
        byte[] qrCodeImageBytes = Base64.getDecoder().decode(qrCodeBase64);
        ByteArrayResource qrCodeResource = new ByteArrayResource(qrCodeImageBytes);

        helper.addInline("qrCodeImage", qrCodeResource, "image/png");

        mailSender.send(mimeMessage);
        return "Sent";
    }
}

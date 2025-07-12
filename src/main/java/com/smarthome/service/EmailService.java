package com.smarthome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Password Reset Request");
            message.setText(buildEmailContent(token));
            message.setFrom("hatruonggiang222@gmail.com");

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);

        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private String buildEmailContent(String token) {
        return "Hi,\n\n" +
                "You requested a password reset. Click the link below to reset your password:\n\n" +
                token + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "RangDong intern";
    }
}
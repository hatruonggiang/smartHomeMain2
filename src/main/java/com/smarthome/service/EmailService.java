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

    public void sendPasswordResetEmail(String email, String name, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText(buildPersonalizedEmailContent(name, resetToken));
            message.setFrom("hatruonggiang222@gmail.com");

            mailSender.send(message);
            System.out.println("Reset email sent to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send reset email: " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }

    private String buildPersonalizedEmailContent(String name, String token) {
        return "Hi " + name + ",\n\n" +
                "You requested a password reset. Click the link below to reset your password:\n\n" +
                token + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "RangDong intern";
    }
    public void sendVerificationEmail(String email, String name, String verificationToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Email Verification Required");
            message.setText(buildVerificationEmailContent(name, verificationToken));
            message.setFrom("hatruonggiang222@gmail.com");

            mailSender.send(message);
            System.out.println("Verification email sent to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }
    private String buildVerificationEmailContent(String name, String token) {
        return "Hi " + name + ",\n\n" +
                "Welcome! Please verify your email by clicking the link below:\n\n" +
                token + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "If you did not register, please ignore this email.\n\n" +
                "Best regards,\n" +
                "RangDong intern";
    }



}
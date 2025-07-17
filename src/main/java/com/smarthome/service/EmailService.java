package com.smarthome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Gửi email đặt lại mật khẩu
    public void sendPasswordResetEmail(String email, String name, String token) {
        try {
            String resetLink = "https://your-frontend.com/reset-password?token=" + token;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Yêu cầu đặt lại mật khẩu");
            message.setText(buildPasswordResetEmailContent(name, resetLink));
            message.setFrom("hatruonggiang222@gmail.com");

            mailSender.send(message);
            System.out.println("Reset email sent to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send reset email: " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }

    private String buildPasswordResetEmailContent(String name, String link) {
        return "Xin chào " + name + ",\n\n" +
                "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng nhấn vào liên kết sau để thực hiện:\n\n" +
                link + "\n\n" +
                "Liên kết này sẽ hết hạn sau 30 phút.\n\n" +
                "Nếu bạn không yêu cầu, hãy bỏ qua email này.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ hỗ trợ SmartHome";
    }

    // Gửi email xác minh
    public void sendVerificationEmail(String email, String name, String token) {
        try {
            String verifyLink = "https://your-frontend.com/verify-email?token=" + token;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Xác minh tài khoản SmartHome");
            message.setText(buildVerificationEmailContent(name, verifyLink));
            message.setFrom("hatruonggiang222@gmail.com");

            mailSender.send(message);
            System.out.println("Verification email sent to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }

    private String buildVerificationEmailContent(String name, String link) {
        return "Xin chào " + name + ",\n\n" +
                "Chào mừng bạn đến với SmartHome! Vui lòng xác minh địa chỉ email bằng cách nhấn vào liên kết dưới đây:\n\n" +
                link + "\n\n" +
                "Liên kết này sẽ hết hạn trong 24 giờ.\n\n" +
                "Nếu bạn không tạo tài khoản, hãy bỏ qua email này.\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ SmartHome";
    }
    public void sendOtpEmail(String toEmail, String name, String otp) {
        try {
            String subject = "Mã xác thực đặt lại mật khẩu";
            String body = String.format(
                    "Xin chào %s,\n\n" +
                            "Mã xác thực để đặt lại mật khẩu của bạn là: %s\n" +
                            "Mã này có hiệu lực trong 10 phút.\n\n" +
                            "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n\n" +
                            "Trân trọng,\n" +
                            "Đội ngũ SmartHome",
                    name, otp
            );

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("hatruonggiang222@gmail.com");

            mailSender.send(message);
            System.out.println("OTP email sent to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
            throw new RuntimeException("Failed to send email");
        }
    }


}

package org.ce.wp.service.impl;

import lombok.RequiredArgsConstructor;
import org.ce.wp.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Parham Ahmadi
 * @since 28.06.23
 */
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender emailSender;

    @Override
    public void sendMain(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Admin@Shortener.com");
        message.setTo(to);
        message.setSubject("Register-Shortener");
        message.setText(text);
        emailSender.send(message);
    }
}

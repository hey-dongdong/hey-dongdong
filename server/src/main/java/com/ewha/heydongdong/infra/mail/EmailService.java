package com.ewha.heydongdong.infra.mail;

public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
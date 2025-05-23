package gr.aueb.cf.taskapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@trial-vywj2lp80rqg7oqz.mlsender.net");
        message.setTo(to);
        message.setSubject(subject);
        message.setText("http://localhost:8080/api/export/pdf/" + body);
        mailSender.send(message);
    }
}

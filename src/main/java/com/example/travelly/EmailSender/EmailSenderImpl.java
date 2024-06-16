package com.example.travelly.EmailSender;

import com.example.travelly.Exceptions.CustomizedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailSenderImpl implements EmailSender{

    public final JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("nogava4457@fna6.com");
        }catch (MessagingException e){
         log.error("Error sending mail", e);
         throw new CustomizedException("Error","Error sending mail");
        }
    }
}

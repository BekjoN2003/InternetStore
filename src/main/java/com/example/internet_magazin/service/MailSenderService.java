package com.example.internet_magazin.service;


import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private final MailSender mailSender;

    public MailSenderService( MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String toAccount,String subject, String content){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAccount);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        mailSender.send((simpleMailMessage));
    }
}

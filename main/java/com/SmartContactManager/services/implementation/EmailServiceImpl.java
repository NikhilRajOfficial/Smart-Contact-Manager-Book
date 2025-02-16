package com.SmartContactManager.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.SmartContactManager.services.EmailService;


@Service
public class EmailServiceImpl implements EmailService {


    //  @Value("${spring.mail.properties.domain_name}")
    //  private String domainName;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        
           SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            simpleMailMessage.setFrom("nikhiljha3016@gmail.com");
            javaMailSender.send(simpleMailMessage);


    }

}

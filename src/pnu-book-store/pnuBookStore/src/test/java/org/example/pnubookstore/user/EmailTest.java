package org.example.pnubookstore.user;

import org.example.pnubookstore.core.config.JavaMailSenderConfig;
import org.example.pnubookstore.domain.user.service.UserEmailVerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailTest {

    private JavaMailSender javaMailSender = new JavaMailSenderConfig().javaMailService();
    private UserEmailVerificationService userEmailVerificationService;
    @Test
    public void emailSendTest(){
//        userEmailVerificationService.sendVerifyEmail("testtestest@pusan.ac.kr");
    }
}

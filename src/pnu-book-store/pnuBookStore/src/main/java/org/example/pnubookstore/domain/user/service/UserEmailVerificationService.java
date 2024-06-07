package org.example.pnubookstore.domain.user.service;

import java.util.concurrent.CompletableFuture;

import org.example.pnubookstore.domain.user.entity.EmailVerification;
import org.example.pnubookstore.domain.user.repository.UserEmailVerificationRedisRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEmailVerificationService {

    private final JavaMailSender javaMailSender;
    private final UserEmailVerificationRedisRepository userEmailVerificationRedisRepository;

    @Value("${spring.mail.username}")
    private String from;

    public EmailVerification isUserEmailValid(String uuid){
        // 파라미터로 넘겨준 정보를 레디스에서 실제로 있는 유저인지 검색
        return userEmailVerificationRedisRepository.findByUuid(uuid);
    }

    public void sendVerifyEmail(String email, String uuid, String domain){
        String subject = "인증 메일";
        String content = getMailContent(uuid, domain);

		    MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail,"UTF-8");
        try{
            mailHelper.setFrom(String.format("부산대학교 중고거래 <%s>", from));
            // 빈에 아이디 설정한 것은 단순히 smtp 인증을 받기 위해 사용 따라서 보내는이(setFrom())반드시 필요
            // 보내는이와 메일주소를 수신하는이가 볼때 모두 표기 되게 원하신다면 아래의 코드를 사용하시면 됩니다.
            //mailHelper.setFrom("보내는이 이름 <보내는이 아이디@도메인주소>");
            mailHelper.setTo(email);
            mailHelper.setSubject(subject);
            mailHelper.setText(content, true);
            // true는 html을 사용하겠다는 의미입니다.

            // 비동기로 메일 보내기
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                javaMailSender.send(mail);
            });

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMailContent(String uuid, String domain) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n")
            .append("<html lang=\"en\">\n")
            .append("<head>\n")
            .append("<meta charset=\"UTF-8\">\n")
            .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
            .append("<style>\n")
            .append("body { font-family: Arial, sans-serif; margin: 0; padding: 20px; color: #333; }\n")
            .append(".container { max-width: 600px; margin: auto; background: #f8f8f8; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n")
            .append("    h1 { color: #007bff; }\n")
            .append("    p { line-height: 1.6; }\n")
            .append(".footer { margin-top: 20px; padding-top: 20px; border-top: 1px solid #eee; font-size: 0.9em; color: #666; }\n")
            .append("</style>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("<div class=\"container\">\n")
            .append("<h1>부산대학교 중고서적 거래 서비스 이메일 인증</h1>\n")
            .append("<p>안녕하세요. 이메일 인증 메일입니다. 아래 링크를 클릭해 회원가입을 진행해주시면 됩니다.</p>\n")
            .append(String.format("<a href=\"%s/signUp/after-email?uuid=%s\">링크 클릭</a>", domain, uuid));
        return sb.toString();
    }
}

package org.example.pnubookstore.domain.user.service.impl;

import jakarta.mail.internet.MimeMessage;
import java.util.UUID;
import org.example.pnubookstore.core.exception.BaseExceptionStatus;
import org.example.pnubookstore.core.exception.Exception400;
import org.example.pnubookstore.domain.user.UserExceptionStatus;
import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.example.pnubookstore.domain.user.entity.EmailVerification;
import org.example.pnubookstore.domain.user.repository.UserEmailVerificationRedisRepository;
import org.example.pnubookstore.domain.user.repository.UserJpaRepository;
import org.example.pnubookstore.domain.user.service.UserEmailVerificationService;
import org.example.pnubookstore.domain.user.service.UserService;
import org.example.pnubookstore.domain.user.entity.User;
import org.example.pnubookstore.domain.base.constant.Role;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
	private final UserJpaRepository userJpaRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserEmailVerificationService userEmailVerificationService;
	private final UserEmailVerificationRedisRepository userEmailVerificationRedisRepository;

	@Transactional
	public void createUser(CreateUserDto userDto) {
		// 이미 존재하는 이메일인지 확인
		if (userJpaRepository.existsByEmailOrNickname(userDto.email(), userDto.nickname())){
			throw new Exception400(UserExceptionStatus.USERNAME_ALREADY_USED);
		}

		// 회원 생성
		User user =  userJpaRepository.save(User.builder()
			.email(userDto.email())
			.password(passwordEncoder.encode(userDto.password()))
			.role(Role.ROLE_USER)
			.nickname(userDto.nickname())
			.build());
	}

	public void emailVerify(String email, HttpServletRequest request){
		boolean matched = email.matches("^[a-zA-Z0-9._%+-]+@pusan\\.ac\\.kr$");
		if(!matched){
			throw new IllegalArgumentException("유효 하지 않은 메일 형식 입니다");
		}

		// 회원 임시 uuid 생성
		String uuid = UUID.randomUUID().toString();
		// uuid와 id를 redis에 저장
		userEmailVerificationRedisRepository.save(new EmailVerification(uuid, email));
		// 메일 보내기
		userEmailVerificationService.sendVerifyEmail(email, uuid, getDomain(request));

	}
	private String getDomain(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();

		sb.append(scheme).append("://").append(serverName);
		if (serverPort != 80 && serverPort != 443) {
			sb.append(":").append(serverPort);
		}

		return sb.toString();
	}
}

package org.example.pnubookstore.domain.user.service;

import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface UserService {
	void createUser(CreateUserDto createUserDto);
	void emailVerify(String email, HttpServletRequest request);
//	void afterEmailForm(CreateUserDto createUserDto);

}

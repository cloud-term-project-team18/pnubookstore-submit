package org.example.pnubookstore.core.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if(session != null){
			// 세션이 존재하면 현재 인증 상태 확인
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
				CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
			}
		}

		filterChain.doFilter(request, response);
	}
}

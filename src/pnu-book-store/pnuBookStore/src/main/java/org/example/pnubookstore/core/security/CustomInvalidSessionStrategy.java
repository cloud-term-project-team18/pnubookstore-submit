package org.example.pnubookstore.core.security;

import java.io.IOException;

import org.springframework.security.web.session.InvalidSessionStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws
		IOException,
		ServletException {
		// 현재 세션 제거
		request.getSession().invalidate();
		response.sendRedirect("/");
	}
}

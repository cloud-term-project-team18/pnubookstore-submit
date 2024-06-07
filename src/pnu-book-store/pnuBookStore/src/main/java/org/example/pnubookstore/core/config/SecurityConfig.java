package org.example.pnubookstore.core.config;

import org.example.pnubookstore.core.security.CustomInvalidSessionStrategy;
import org.example.pnubookstore.core.security.SessionAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(new SessionAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// CSRF 해제
		http.csrf(CsrfConfigurer::disable);

		// iframe 거부
		http.headers(httpSecurityHeadersConfigurer ->
			httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

		// cors 재설정
		http.cors(httpSecurityCorsConfigurer ->
			httpSecurityCorsConfigurer.configurationSource(configurationSource()));

		// 세션 관리
		http.sessionManagement(session -> session
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 필요할때만 사용
			.invalidSessionStrategy(new CustomInvalidSessionStrategy())
			.maximumSessions(1) 				// 최대 세션 수, 1로 설정 시 세션 중복 방지
			.maxSessionsPreventsLogin(false)		// 기존 세션을 제거.
			.expiredUrl("/login?expired") 			// 세션 종료시 리다이렉트 할 URL
 		);

		http.formLogin(login -> login
			.loginPage("/login")                  // 로그인 페이지 URL
			.failureHandler(loginFailureHandler())
			.successHandler(loginSuccessHandler())
			.permitAll()                          			// 모든 사용자에게 로그인 페이지 접근 허용
		);

		http.logout(logout -> logout
			.logoutSuccessUrl("/")// 로그아웃 성공 후 리다이렉트할 URL
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
		);


		http.exceptionHandling(exceptionHandling -> exceptionHandling
			// .authenticationEntryPoint()  // 인증되지 않은 접근 시 처리
			.accessDeniedHandler(customAccessDeniedHandler())  // 권한 없는 접근 시 처리
			.authenticationEntryPoint(((request, response, authException) -> {
				StringBuilder sb = new StringBuilder();
				String queryString = request.getQueryString();
				sb.append(request.getRequestURI());

				if(queryString != null && queryString.contains("error")) {
					queryString = queryString.replaceFirst("([&?])error=[^&]*", "");
				}
				if(queryString != null && queryString.contains("redirect")) {
					queryString = queryString.replaceFirst("([&?])redirect=[^&]*", "");
					sb.append("&").append(queryString);
				}

				response.sendRedirect("/login?redirect="+sb);
			})));

		// 인증, 권한 필터 설정 -> 현재는 모두 허가
		http.authorizeHttpRequests(authorize ->
			authorize
				.requestMatchers(
					new AntPathRequestMatcher("/product-register/**"),
					new AntPathRequestMatcher("myPage"),
					new AntPathRequestMatcher("order/**")
				).authenticated()
				.anyRequest()
				.permitAll());

		return http.build();
	}

	private CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*"); // GET, POST, UPDATE, DELETE 모두 허용
		configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
		configuration.setAllowCredentials(true); // 클라이언트에게 쿠키 요청 허용
		configuration.addExposedHeader("Authorization"); // 클라이언트에게 Authorization 헤더 접근 허용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	private AccessDeniedHandler customAccessDeniedHandler() {
		return ((request, response, accessDeniedException) -> {
			String errorUrl = "/error/forbidden-page";
			response.sendRedirect(errorUrl);
		});
	}
	private AuthenticationSuccessHandler loginSuccessHandler() {
		return (request, response, authentication) -> {
			request.getSession(true);  // 로그인 성공 시 세션 강제 생성
			String redirectUrl = request.getParameter("redirect");
			if (redirectUrl != null && !redirectUrl.isEmpty()) {
				response.sendRedirect(redirectUrl);
			} else {
				response.sendRedirect("/");
			}
		};
	}

	private AuthenticationFailureHandler loginFailureHandler() {
		return (request, response, authentication) -> {
			HttpSession session = request.getSession(false);
			if (session != null) session.invalidate();
			String redirectUrl = request.getParameter("redirect");
			if (redirectUrl != null && !redirectUrl.isEmpty()) {
				response.sendRedirect("/login?error&redirect="+redirectUrl);
			} else {
				response.sendRedirect("/login?error");
			}
		};
	}
}

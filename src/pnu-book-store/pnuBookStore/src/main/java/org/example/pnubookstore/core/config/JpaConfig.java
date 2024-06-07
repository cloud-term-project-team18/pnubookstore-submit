package org.example.pnubookstore.core.config;

import java.util.Optional;

import org.example.pnubookstore.core.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
	@Bean
	public AuditorAware<String> auditorAware() {
	    return () -> Optional.of(SecurityContextHolder.getContext())
	                .map(SecurityContext::getAuthentication)
	                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken))
	                .map(Authentication::getPrincipal)
	                .map(CustomUserDetails.class::cast)
	                .map(CustomUserDetails::getUsername);
	}
}

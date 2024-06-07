package org.example.pnubookstore.domain.base.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ROLE_ADMIN("ROLE_ADMIN", "관리자"),
	ROLE_USER("ROLE_USER", "사용자");

	private final String role;
	private final String name;
}

package org.example.pnubookstore.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserDto(
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@pusan\\.ac\\.kr$")
	@NotNull
	String email,
	String password,
	String nickname
) {
}

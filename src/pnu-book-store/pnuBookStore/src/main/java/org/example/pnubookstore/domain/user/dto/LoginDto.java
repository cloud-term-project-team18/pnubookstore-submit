package org.example.pnubookstore.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public record LoginDto(
	@NotNull String username,
	@NotNull String password
) {
}

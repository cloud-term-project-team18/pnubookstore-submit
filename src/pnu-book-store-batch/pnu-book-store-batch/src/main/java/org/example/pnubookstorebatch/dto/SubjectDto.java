package org.example.pnubookstorebatch.dto;

import lombok.Builder;

public record SubjectDto(
	String college,
	String department, // 학과(부)
	String subjectName,
	String professor
) {
	@Builder
	public SubjectDto {
	}
}

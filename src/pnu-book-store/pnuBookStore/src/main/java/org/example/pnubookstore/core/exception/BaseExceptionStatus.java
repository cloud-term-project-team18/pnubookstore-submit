package org.example.pnubookstore.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface BaseExceptionStatus {
	int getErrorCode();
	String getErrorMessage();
}

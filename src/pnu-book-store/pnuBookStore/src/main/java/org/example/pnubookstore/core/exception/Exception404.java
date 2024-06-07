package org.example.pnubookstore.core.exception;

import org.springframework.http.HttpStatus;

public class Exception404 extends ClientException{
	public Exception404(final BaseExceptionStatus exceptionStatus) {
		super(exceptionStatus.getErrorMessage());
	}
	public Exception404(final String message) {
		super(message);
	}

	@Override
	String body() {
		return getMessage();
	}

	@Override
	HttpStatus status() {
		return HttpStatus.NOT_FOUND;
	}
}

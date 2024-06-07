package org.example.pnubookstore.core.exception;

import org.springframework.http.HttpStatus;

public class Exception400 extends ClientException{
	public Exception400(final BaseExceptionStatus exceptionStatus) {
		super(exceptionStatus.getErrorMessage());
	}
	@Override
	String body() {
		return getMessage();
	}

	@Override
	HttpStatus status() {
		return HttpStatus.BAD_REQUEST;
	}
}

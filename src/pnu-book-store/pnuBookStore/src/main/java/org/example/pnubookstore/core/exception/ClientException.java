package org.example.pnubookstore.core.exception;

import org.springframework.http.HttpStatus;

public abstract class ClientException extends RuntimeException{

	public ClientException(String message) {super(message);}

	abstract String body();

	abstract HttpStatus status();
}

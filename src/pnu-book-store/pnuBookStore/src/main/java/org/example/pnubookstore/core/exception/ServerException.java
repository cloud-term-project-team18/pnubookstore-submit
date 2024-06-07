package org.example.pnubookstore.core.exception;

import org.springframework.http.HttpStatus;

public abstract class ServerException extends RuntimeException{

	public ServerException(String message) {super(message);}

	public String body() {return null;}

	public HttpStatus status() { return null;}
}
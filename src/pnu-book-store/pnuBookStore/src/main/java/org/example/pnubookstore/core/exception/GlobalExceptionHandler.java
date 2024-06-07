package org.example.pnubookstore.core.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// @ExceptionHandler(ClientException.class)
	// public String handleClientException(
	// 	final ClientException e,
	// 	final HttpServletRequest request) {
	// 	request.getRequestURL();
	// 	// return
	// }
}

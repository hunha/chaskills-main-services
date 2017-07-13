package com.realife.services.exceptions;

import org.springframework.http.HttpStatus;

import com.realife.services.models.ErrorResponse;

public abstract class ResponseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseException() {
		super();
	}

	public ResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseException(String message) {
		super(message);
	}

	public ResponseException(Throwable cause) {
		super(cause);
	}
	
	public abstract ErrorResponse getResponse();
	
	public abstract HttpStatus getHttpStatus();
}

package com.realife.services.exceptions;

import org.springframework.http.HttpStatus;

import com.realife.services.models.ErrorResponse;

public class BadRequestException extends ResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
		super();
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}

	@Override
	public ErrorResponse getResponse() {
		return new ErrorResponse("Bad Request");
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}

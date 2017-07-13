package com.realife.services.exceptions;

import org.springframework.http.HttpStatus;

import com.realife.services.models.ErrorResponse;

public class NotFoundException extends ResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}
	
	@Override
	public ErrorResponse getResponse() {
		return new ErrorResponse("Not Found");
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}

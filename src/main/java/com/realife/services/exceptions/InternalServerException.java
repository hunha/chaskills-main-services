package com.realife.services.exceptions;

import org.springframework.http.HttpStatus;

import com.realife.services.models.ErrorResponse;

public class InternalServerException extends ResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InternalServerException() {
		super();
	}

	public InternalServerException(String message) {
		super(message);
	}

	@Override
	public ErrorResponse getResponse() {
		return new ErrorResponse(this.getMessage());
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}

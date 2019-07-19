package com.realife.services.models;

public class ErrorResponse extends BaseResponse {

	private Object errors;

	public ErrorResponse() {

	}

	public ErrorResponse(Object errors) {
		this.errors = errors;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}
}

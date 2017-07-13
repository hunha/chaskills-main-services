package com.realife.services.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends BaseResponse {

	private Object errors;

	public ErrorResponse() {

	}

	public ErrorResponse(Object errors) {
		this.errors = errors;
	}
}

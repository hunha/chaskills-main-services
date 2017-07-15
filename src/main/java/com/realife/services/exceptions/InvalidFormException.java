package com.realife.services.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.realife.services.models.ErrorResponse;

public class InvalidFormException extends ResponseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<String, List<String>> errors;

	public InvalidFormException() {
		super();
		errors = new HashMap<String, List<String>>();
	}

	public InvalidFormException(BindingResult result) {
		super();
		this.errors = new HashMap<String, List<String>>();
		addError(result);
	}
	
	public InvalidFormException(HashMap<String, List<String>> errors) {
		super();
		this.errors = errors; 
	}

	public void addError(BindingResult result) {
		for (FieldError error : result.getFieldErrors()) {
			addError(error.getField(), error.getDefaultMessage());
		}
	}

	public void addError(String field, String message) {
		if (errors.containsKey(field)) {
			List<String> messages = errors.get(field);
			messages.add(message);
			errors.replace(field, messages);
		} else {
			List<String> messages = new ArrayList<String>();
			messages.add(message);
			errors.put(field, messages);
		}
	}

	@Override
	public ErrorResponse getResponse() {
		return new ErrorResponse(errors);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}
}

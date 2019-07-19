package com.realife.services.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realife.services.exceptions.InvalidFormException;
import com.realife.services.exceptions.ResponseException;
import com.realife.services.models.ErrorResponse;

public class BaseController {

	@Autowired
	protected ModelMapper modelMapper;

	@Autowired
	protected ObjectMapper jsonMapper;

	@ExceptionHandler(ResponseException.class)
	public ResponseEntity<ErrorResponse> handleError(ResponseException ex) {

		return new ResponseEntity<ErrorResponse>(ex.getResponse(), ex.getHttpStatus());
	}

	protected void throwInvalidFormException(String field, String message) {
		HashMap<String, List<String>> errors = new HashMap<String, List<String>>();
		errors.put(field, new ArrayList<String>(Arrays.asList(message)));
		throw new InvalidFormException(errors);
	}
}

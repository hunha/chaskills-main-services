package com.realife.services.base;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}

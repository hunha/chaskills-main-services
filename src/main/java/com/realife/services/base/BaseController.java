package com.realife.services.base;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	protected ObjectMapper jsonMapper;

	public Object errorHandling(Map<String, Object> parameters) {
		return null;
	}
}

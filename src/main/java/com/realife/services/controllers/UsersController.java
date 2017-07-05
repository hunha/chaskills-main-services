package com.realife.services.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.realife.services.base.BaseController;

@RestController
public class UsersController extends BaseController {

	@RequestMapping("/")
	@HystrixCommand(groupKey = "users", commandKey = "users.filter")
	public String index() throws Exception {
		return "Greetings from Spring Boot!";
	}
}

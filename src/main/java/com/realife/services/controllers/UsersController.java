package com.realife.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.realife.services.base.BaseController;
import com.realife.services.domains.User;
import com.realife.services.repositories.UserRepository;
import com.realife.services.user.models.UserResponse;
import com.realife.services.user.models.UsersResponse;

import lombok.val;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController {

	@Autowired
	UserRepository _userRepository;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.filter")
	public UsersResponse filter() {
		val response = new UsersResponse();

		val users = _userRepository.findAll();
		for (User user : users) {
			UserResponse userResponse = modelMapper.map(user, UserResponse.class);
			response.add(userResponse);
		}

		return response;
	}
}

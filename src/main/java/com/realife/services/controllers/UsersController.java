package com.realife.services.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.realife.services.base.BaseController;
import com.realife.services.domains.User;
import com.realife.services.services.UserService;
import com.realife.services.user.models.UserFilterRequest;
import com.realife.services.user.models.UserResponse;
import com.realife.services.user.models.UsersResponse;
import com.sun.jersey.api.NotFoundException;

import lombok.val;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController {

	@Autowired
	UserService _userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.filter")
	public UsersResponse filter(UserFilterRequest filter) {

		val response = new UsersResponse();

		List<User> users = _userService.findAll(filter);
		for (User user : users) {
			UserResponse userResponse = modelMapper.map(user, UserResponse.class);
			response.add(userResponse);
		}

		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.filter")
	public UserResponse getById(@PathVariable("id") Long id) {

		val user = _userService.findById(id);
		if (user == null)
			throw new NotFoundException();

		UserResponse response = modelMapper.map(user, UserResponse.class);
		return response;
	}
}

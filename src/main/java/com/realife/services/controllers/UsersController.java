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
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.filter")
	public UsersResponse filter() {
		val users = _userRepository.findAll();
		val response = new UsersResponse();

		for (User user : users) {
			val userResponse = new UserResponse();
			userResponse.setId(user.getId());
			userResponse.setFirstName(user.getFirstName());
			userResponse.setLastName(user.getLastName());
			userResponse.setEmail(user.getEmail());
			userResponse.setCreatedAt(user.getCreatedAt());
			response.add(userResponse);
		}
		
		return response;
	}
}

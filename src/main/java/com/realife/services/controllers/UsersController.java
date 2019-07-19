package com.realife.services.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.realife.services.base.BaseController;
import com.realife.services.domains.User;
import com.realife.services.exceptions.*;
import com.realife.services.models.users.*;
import com.realife.services.services.UserService;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.filter")
	public UsersResponse filter(UserFilterRequest filter) {

		UsersResponse response = new UsersResponse();

		List<User> users = userService.findAll(filter);
		for (User user : users) {
			UserResponse userResponse = modelMapper.map(user, UserResponse.class);
			response.add(userResponse);
		}

		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.get_by_id")
	public UserResponse getById(@PathVariable("id") Long id) {

		User user = userService.findById(id);
		if (user == null)
			throw new NotFoundException();

		UserResponse response = modelMapper.map(user, UserResponse.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	@HystrixCommand(groupKey = "users", commandKey = "users.create")
	public UserResponse create(@Valid @RequestBody UserRequest model, BindingResult result) throws Exception {

		if (result.hasErrors())
			throw new InvalidFormException(result);

		User user = userService.findByEmail(model.getEmail());
		if (user != null)
			throwInvalidFormException("email", "is existed");

		user = modelMapper.map(model, User.class);
		user.setId(null);
		user.setPasswordDigest(model.getPassword());
		user = userService.save(user);
		if (user == null)
			throw new InternalServerException();

		UserResponse response = modelMapper.map(user, UserResponse.class);
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.update")
	public UserResponse update(@PathVariable("id") Long id, @Valid @RequestBody UserRequest model,
			BindingResult result) throws Exception {

		User user = userService.findById(id);
		if (user == null)
			throw new NotFoundException();

		// TODO: Update by JSON body fields.
		if (!StringUtils.isBlank(model.getFirstName()))
			user.setFirstName(model.getFirstName());

		if (!StringUtils.isBlank(model.getLastName()))
			user.setLastName(model.getLastName());

		if (!StringUtils.isBlank(model.getPassword()))
			user.setPasswordDigest(model.getPassword());

		if (!StringUtils.isBlank(model.getRememberDigest()))
			user.setRememberDigest(model.getRememberDigest());

		user = userService.save(user);
		if (user == null)
			throw new InternalServerException();

		UserResponse response = modelMapper.map(user, UserResponse.class);
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@HystrixCommand(groupKey = "users", commandKey = "users.delete")
	public void delete(@PathVariable("id") Long id) throws Exception {

		User user = userService.findById(id);
		if (user == null)
			throw new NotFoundException();

		userService.delete(id);
	}
}

package com.realife.services.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.realife.services.base.BaseController;
import com.realife.services.domains.Characterise;
import com.realife.services.domains.User;
import com.realife.services.exceptions.InternalServerException;
import com.realife.services.exceptions.InvalidFormException;
import com.realife.services.exceptions.NotFoundException;
import com.realife.services.models.characterises.CharacteriseFilterRequest;
import com.realife.services.models.characterises.CharacteriseRequest;
import com.realife.services.models.characterises.CharacteriseResponse;
import com.realife.services.models.characterises.CharacterisesResponse;
import com.realife.services.services.CharacteriseService;
import com.realife.services.services.UserService;

import lombok.val;

@RestController
@RequestMapping("/characterises")
public class CharacterisesController extends BaseController {

	@Autowired
	private CharacteriseService characteriseService;

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "characterises", commandKey = "characterises.filter")
	public CharacterisesResponse filter(CharacteriseFilterRequest filter) {

		val response = new CharacterisesResponse();

		List<Characterise> characterises = characteriseService.findAll(filter);
		for (Characterise characterise : characterises) {
			CharacteriseResponse characteriseResponse = modelMapper.map(characterise, CharacteriseResponse.class);
			response.add(characteriseResponse);
		}

		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@HystrixCommand(groupKey = "characterises", commandKey = "characterises.get_by_id")
	public CharacteriseResponse getById(@PathVariable("id") Long id) {

		val characterise = characteriseService.findById(id);
		if (characterise == null)
			throw new NotFoundException();

		CharacteriseResponse response = modelMapper.map(characterise, CharacteriseResponse.class);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	@HystrixCommand(groupKey = "characterises", commandKey = "characterises.create")
	public CharacteriseResponse create(@Valid @RequestBody CharacteriseRequest model, BindingResult result)
			throws Exception {

		if (result.hasErrors())
			throw new InvalidFormException(result);

		User user = userService.findById(model.getUserId());
		if (user == null)
			throw new NotFoundException();

		Characterise characterise = characteriseService.findByName(user.getId(), model.getName());
		if (characterise != null)
			throwInvalidFormException("name", "is existed");

		characterise = modelMapper.map(model, Characterise.class);
		characterise.setId(null);
		characterise.setLevel(Characterise.DEFAULT_LEVEL);
		characterise.setPoints(Characterise.DEFAULT_POINTS);
		characterise = characteriseService.save(characterise);
		if (characterise == null)
			throw new InternalServerException();

		CharacteriseResponse response = modelMapper.map(characterise, CharacteriseResponse.class);
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	@HystrixCommand(groupKey = "characterises", commandKey = "characterises.update")
	public CharacteriseResponse update(@PathVariable("id") Long id, @Valid @RequestBody CharacteriseRequest model,
			BindingResult result) throws Exception {

		Characterise characterise = characteriseService.findById(id);
		if (characterise == null)
			throw new NotFoundException();

		// TODO: Update by JSON body fields.
		if (!StringUtils.isBlank(model.getName()))
			characterise.setName(model.getName());

		if (!StringUtils.isBlank(model.getDescription()))
			characterise.setDescription(model.getDescription());

		characterise = characteriseService.save(characterise);
		if (characterise == null)
			throw new InternalServerException();

		CharacteriseResponse response = modelMapper.map(characterise, CharacteriseResponse.class);
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@HystrixCommand(groupKey = "characterises", commandKey = "characterises.delete")
	public void delete(@PathVariable("id") Long id) throws Exception {

		Characterise characterises = characteriseService.findById(id);
		if (characterises == null)
			throw new NotFoundException();

		characteriseService.delete(id);
	}
}

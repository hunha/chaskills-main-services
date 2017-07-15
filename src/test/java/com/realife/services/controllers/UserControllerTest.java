package com.realife.services.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realife.services.base.Application;
import com.realife.services.domains.User;
import com.realife.services.models.users.UserRequest;
import com.realife.services.models.users.UserResponse;
import com.realife.services.models.users.UsersResponse;
import com.realife.services.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	private ObjectMapper jsonMapper;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserService userService;

	private UserRequest userRequest;
	private User user;
	private Long createdUserId;

	@Before
	public void init() {
		if (user == null) {
			user = new User();
			user.setEmail("hungtest" + Math.random() + "@gmail.com");
			user.setFirstName("Hung");
			user.setLastName("Nguyen");
			user.setPasswordDigest("123456");
			user = userService.save(user);
		}

		if (userRequest == null) {
			userRequest = new UserRequest();
			userRequest.setEmail("hungtest" + Math.random() + "@gmail.com");
			userRequest.setFirstName("Hung 2");
			userRequest.setLastName("Meo");
			userRequest.setPassword("12346");
		}
	}

	@After
	public void dispose() {
		if (user != null) {
			userService.delete(user.getId());
			user = null;
		}

		if (createdUserId != null) {
			userService.delete(createdUserId);
			createdUserId = null;
		}
	}

	@Test
	public void whenFilter_thenFound() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		UsersResponse response = getResponse(result, UsersResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, not(empty()));
	}

	private <T> T getResponse(MvcResult result, Class<T> type) {

		try {
			return jsonMapper.readValue(result.getResponse().getContentAsString(), type);
		} catch (Exception e) {
		}

		return null;
	}

	@Test
	public void givenRightFirstName_whenFilter_thenFound() throws Exception {
		String path = "/users?firstName=" + user.getFirstName();

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON)).andReturn();
		UsersResponse response = getResponse(result, UsersResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, not(empty()));
	}

	@Test
	public void givenWrongFirstName_whenFilter_thenNotFound() throws Exception {
		String path = "/users?firstName=NotFound";

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON)).andReturn();
		UsersResponse response = getResponse(result, UsersResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, is(empty()));
	}

	@Test
	public void whenGetById_thenFound() throws Exception {
		String path = "/users/" + user.getId();

		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON)).andReturn();
		UserResponse response = getResponse(result, UserResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, is(notNullValue()));
		assertThat(response.getId(), equalTo(user.getId()));
	}

	@Test
	public void whenCreate_thenCorrect() throws Exception {
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		UserResponse response = getResponse(result, UserResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.CREATED.value()));
		assertThat(response, is(notNullValue()));
		assertThat(response.getEmail(), equalTo(userRequest.getEmail()));

		createdUserId = response.getId();
	}

	@Test
	public void whenUpdate_thenCorrect() throws Exception {
		String path = "/users/" + user.getId();
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail(user.getEmail());
		userRequest.setFirstName("Hung 2");
		userRequest.setLastName(user.getFirstName());
		userRequest.setPassword("12346");
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		UserResponse response = getResponse(result, UserResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, is(notNullValue()));
		assertThat(response.getFirstName(), equalTo(userRequest.getFirstName()));
	}

	@Test
	public void whenDelete_thenCorrect() throws Exception {
		String path = "/users/" + user.getId();
		user = null;

		MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(path)).andReturn();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
	}
}

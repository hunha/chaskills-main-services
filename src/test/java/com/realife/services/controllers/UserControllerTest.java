package com.realife.services.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.BDDMockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realife.services.domains.User;
import com.realife.services.models.ErrorResponse;
import com.realife.services.models.users.UserFilterRequest;
import com.realife.services.models.users.UserRequest;
import com.realife.services.models.users.UserResponse;
import com.realife.services.models.users.UsersResponse;
import com.realife.services.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
public class UserControllerTest {

	@Mock
	UserService userService;

	@Spy
	ModelMapper modelMapper;

	@Spy
	ObjectMapper jsonMapper;

	@InjectMocks
	UsersController usersController;

	private MockMvc mockMvc;
	private User user;
	private UserRequest userRequest;

	@Before
	public void init() {
		// this must be called for the @Mock annotations above to be processed
		// and for the mock service to be injected into the controller under
		// test.
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();

		user = new User();
		user.setId(new Long(1));
		user.setEmail("characterise_controller_test" + Math.random() + "@gmail.com");
		user.setFirstName("Hung");
		user.setLastName("Nguyen");
		user.setPasswordDigest("123456");

		userRequest = new UserRequest();
		userRequest.setEmail(user.getEmail());
		userRequest.setFirstName(user.getFirstName());
		userRequest.setLastName(user.getLastName());
		userRequest.setPassword("12346");
	}

	@Test
	public void whenFilter_thenFound() throws Exception {
		List<User> mockResult = new ArrayList<User>(Arrays.asList(user));
		given(userService.findAll(isA(UserFilterRequest.class))).willReturn(mockResult);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON))
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
	public void givenExistId_whenGetById_thenFound() throws Exception {
		given(userService.findById(user.getId())).willReturn(user);
		String path = "/users/" + user.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		UserResponse response = getResponse(result, UserResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, is(notNullValue()));
		assertThat(response.getId(), equalTo(user.getId()));
	}

	@Test
	public void givenUnexistId_whenGetById_thenNotFound() throws Exception {
		given(userService.findById(user.getId())).willReturn(null);
		String path = "/users/" + user.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}

	@Test
	public void whenCreate_thenSuccess() throws Exception {
		given(userService.save(isA(User.class))).willReturn(user);
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		UserResponse response = getResponse(result, UserResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.CREATED.value()));
		assertThat(response, is(notNullValue()));
		assertThat(response.getEmail(), equalTo(userRequest.getEmail()));
		assertThat(response.getFirstName(), equalTo(userRequest.getFirstName()));
		assertThat(response.getLastName(), equalTo(userRequest.getLastName()));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenExistedEmail_whenCreate_thenFailure() throws Exception {
		given(userService.findByEmail(userRequest.getEmail())).willReturn(user);
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("email"));
		assertThat(errors.get("email").get(0), equalTo("is existed"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenFirstNameNull_whenCreate_thenFailure() throws Exception {
		userRequest.setFirstName(null);
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("firstName"));
		assertThat(errors.get("firstName").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenFirstNameBlank_whenCreate_thenFailure() throws Exception {
		userRequest.setFirstName("");
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("firstName"));
		assertThat(errors.get("firstName").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenFirstNameTooLong_whenCreate_thenFailure() throws Exception {
		userRequest.setFirstName(String.join("", Collections.nCopies(51, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("firstName"));
		assertThat(errors.get("firstName").get(0), equalTo("length must be between 0 and 50"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenLastNameTooLong_whenCreate_thenFailure() throws Exception {
		userRequest.setLastName(String.join("", Collections.nCopies(51, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("lastName"));
		assertThat(errors.get("lastName").get(0), equalTo("length must be between 0 and 50"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenEmailNull_whenCreate_thenFailure() throws Exception {
		userRequest.setEmail(null);
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("email"));
		assertThat(errors.get("email").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenEmailBlank_whenCreate_thenFailure() throws Exception {
		userRequest.setEmail("");
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("email"));
		assertThat(errors.get("email").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenEmailTooLong_whenCreate_thenFailure() throws Exception {
		userRequest.setEmail(String.join("", Collections.nCopies(256, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("email"));
		assertThat(errors.get("email").get(0), equalTo("length must be between 0 and 255"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenPasswordNull_whenCreate_thenFailure() throws Exception {
		userRequest.setPassword(null);
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("password"));
		assertThat(errors.get("password").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenPasswordBlank_whenCreate_thenFailure() throws Exception {
		userRequest.setPassword("");
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("password"));
		assertThat(errors.get("password").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenPasswordTooLong_whenCreate_thenFailure() throws Exception {
		userRequest.setPassword(String.join("", Collections.nCopies(256, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("password"));
		assertThat(errors.get("password").get(0), equalTo("length must be between 0 and 255"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenRememberDigestTooLong_whenCreate_thenFailure() throws Exception {
		userRequest.setRememberDigest(String.join("", Collections.nCopies(256, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("rememberDigest"));
		assertThat(errors.get("rememberDigest").get(0), equalTo("length must be between 0 and 255"));
	}

	@Test
	public void whenUpdate_thenSuccess() throws Exception {
		given(userService.findById(user.getId())).willReturn(user);
		given(userService.save(isA(User.class))).willReturn(user);
		String path = "/users/" + user.getId();
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		UserResponse response = getResponse(result, UserResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, is(notNullValue()));
		assertThat(response.getFirstName(), equalTo(userRequest.getFirstName()));
	}

	@Test
	public void givenUnexistId_whenUpdate_thenFailure() throws Exception {
		given(userService.findById(user.getId())).willReturn(null);
		String path = "/users/" + user.getId();
		String jsonRequest = jsonMapper.writeValueAsString(userRequest);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}

	@Test
	public void whenDelete_thenSuccess() throws Exception {
		given(userService.findById(user.getId())).willReturn(user);
		String path = "/users/" + user.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(path)).andReturn();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
	}

	@Test
	public void givenUnexistId_whenDelete_thenFailure() throws Exception {
		given(userService.findById(user.getId())).willReturn(null);
		String path = "/users/" + user.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(path)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}
}

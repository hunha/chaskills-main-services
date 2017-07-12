package com.realife.services.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.realife.services.base.Application;
import com.realife.services.domains.User;
import com.realife.services.services.UserService;
import com.realife.services.user.models.UserRequest;
import com.realife.services.user.models.UserResponse;
import com.realife.services.user.models.UsersResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserService userService;

	private UserRequest userRequest;
	private User user;
	private User userToBeDeleted;
	private Long createdUserId;

	@Before
	public void init() {
		user = new User();
		user.setEmail("hungtest" + Math.random() + "@gmail.com");
		user.setFirstName("Hung");
		user.setLastName("Nguyen");
		user.setPasswordDigest("123456");
		user = userService.save(user);

		userToBeDeleted = new User();
		userToBeDeleted.setEmail("hungtest" + Math.random() + "@gmail.com");
		userToBeDeleted.setFirstName("Hung");
		userToBeDeleted.setLastName("Nguyen");
		userToBeDeleted.setPasswordDigest("123456");
		userToBeDeleted = userService.save(userToBeDeleted);

		userRequest = new UserRequest();
		userRequest.setEmail("hungtest" + Math.random() + "@gmail.com");
		userRequest.setFirstName("Hung 2");
		userRequest.setLastName("Meo");
		userRequest.setPassword("12346");
	}

	@After
	public void dispose() {
		userService.delete(user.getId());

		if (createdUserId != null)
			userService.delete(createdUserId);
	}

	@Test
	public void shouldGetFilter() throws Exception {
		ResponseEntity<UsersResponse> response = restTemplate.getForEntity("/users", UsersResponse.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody(), not(empty()));
	}

	@Test
	public void shouldGetById() throws Exception {
		String path = "/users/" + user.getId();
		ResponseEntity<UserResponse> response = restTemplate.getForEntity(path, UserResponse.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getId(), equalTo(user.getId()));
	}

	@Test
	public void shouldCreateUser() throws Exception {
		ResponseEntity<UserResponse> response = restTemplate.postForEntity("/users", userRequest, UserResponse.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getEmail(), equalTo(userRequest.getEmail()));

		createdUserId = response.getBody().getId();
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		String path = "/users/" + user.getId();
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail(user.getEmail());
		userRequest.setFirstName("Hung 2");
		userRequest.setLastName(user.getFirstName());
		userRequest.setPassword("12346");

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<UserRequest> request = new HttpEntity<UserRequest>(userRequest, headers);

		ResponseEntity<UserResponse> response = restTemplate.exchange(path, HttpMethod.PUT, request,
				UserResponse.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response, is(notNullValue()));
		assertThat(response.getBody().getFirstName(), equalTo(userRequest.getFirstName()));
	}

	@Test
	public void shouldDeleteUser() throws Exception {
		String path = "/users/" + userToBeDeleted.getId();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> request = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.DELETE, request, String.class);
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}
}

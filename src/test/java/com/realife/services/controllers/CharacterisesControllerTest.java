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
import com.realife.services.domains.Characterise;
import com.realife.services.domains.User;
import com.realife.services.models.characterises.CharacterisesResponse;
import com.realife.services.services.CharacteriseService;
import com.realife.services.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CharacterisesControllerTest {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper jsonMapper;
	@Autowired
	private CharacteriseService characteriseService;
	@Autowired
	private UserService userService;

	private Characterise characterise;
	private User user;

	@Before
	public void init() {
		if (user == null) {
			user = new User();
			user.setEmail("characterise_controller_test" + Math.random() + "@gmail.com");
			user.setFirstName("Hung");
			user.setLastName("Nguyen");
			user.setPasswordDigest("123456");
			user = userService.save(user);
		}
		
		if (characterise == null) {
			characterise = new Characterise();
			characterise.setId(null);
			characterise.setUserId(user.getId());
			characterise.setName("Skill Controller" + Math.random());
			characterise.setLevel(Characterise.DEFAULT_LEVEL);
			characterise.setPoints(Characterise.DEFAULT_POINTS);
			characteriseService.save(characterise);
		}
	}

	@After
	public void dispose() {
		if (user != null) {
			userService.delete(user.getId());
			user = null;
		}
		
		if (characterise != null) {
			characteriseService.delete(characterise.getId());
			characterise = null;
		}
	}

	@Test
	public void whenGetFilter_thenCorrect() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/characterises").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		CharacterisesResponse response = jsonMapper.readValue(result.getResponse().getContentAsString(),
				CharacterisesResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, not(empty()));
	}
}

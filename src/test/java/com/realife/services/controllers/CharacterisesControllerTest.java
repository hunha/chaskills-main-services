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
import com.realife.services.models.characterises.CharacterisesResponse;
import com.realife.services.services.CharacteriseService;

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
	
	private Characterise characterise;
	
	@Before
	public void init() {
		characterise = new Characterise();
		characterise.setId(null);
		characterise.setUserId(new Long(1));
		characterise.setName("Skill" + Math.random());
		characterise.setLevel(Characterise.DEFAULT_LEVEL);
		characterise.setPoints(Characterise.DEFAULT_POINTS);
		characteriseService.save(characterise);
	}
	
	@After
	public void dispose() {
		characteriseService.delete(characterise.getId());
	}

	@Test
	public void shouldGetFilter() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/characterises").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		CharacterisesResponse response = jsonMapper.readValue(result.getResponse().getContentAsString(),
				CharacterisesResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, not(empty()));
	}
}

package com.realife.services.controllers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
import com.realife.services.domains.Characterise;
import com.realife.services.domains.User;
import com.realife.services.models.ErrorResponse;
import com.realife.services.models.characterises.CharacteriseFilterRequest;
import com.realife.services.models.characterises.CharacteriseRequest;
import com.realife.services.models.characterises.CharacteriseResponse;
import com.realife.services.models.characterises.CharacterisesResponse;
import com.realife.services.services.CharacteriseService;
import com.realife.services.services.UserService;

@RunWith(SpringRunner.class)
public class CharacterisesControllerTest {

	@Mock
	CharacteriseService characteriseService;

	@Mock
	UserService userService;

	@Spy
	ModelMapper modelMapper;

	@Spy
	ObjectMapper jsonMapper;

	@InjectMocks
	CharacterisesController characterisesController;

	private MockMvc mockMvc;
	private User user;
	private Characterise characterise;
	private CharacteriseRequest characteriseRequest;

	@Before
	public void init() {
		// this must be called for the @Mock annotations above to be processed
		// and for the mock service to be injected into the controller under
		// test.
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(characterisesController).build();

		user = new User();
		user.setId(new Long(1));
		user.setEmail("characterise_controller_test" + Math.random() + "@gmail.com");
		user.setFirstName("Hung");
		user.setLastName("Nguyen");
		user.setPasswordDigest("123456");

		characterise = new Characterise();
		characterise.setId(new Long(1));
		characterise.setUserId(user.getId());
		characterise.setName("Skill Controller" + Math.random());
		characterise.setLevel(Characterise.DEFAULT_LEVEL);
		characterise.setPoints(Characterise.DEFAULT_POINTS);
		characterise.setDescription("Skill Description");

		characteriseRequest = new CharacteriseRequest();
		characteriseRequest.setName(characterise.getName());
		characteriseRequest.setDescription(characterise.getDescription());
		characteriseRequest.setUserId(characterise.getUserId());
	}

	@Test
	public void whenGetFilter_thenFound() throws Exception {
		List<Characterise> mockResult = new ArrayList<Characterise>(Arrays.asList(characterise));
		given(characteriseService.findAll(isA(CharacteriseFilterRequest.class))).willReturn(mockResult);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/characterises").accept(MediaType.APPLICATION_JSON)).andReturn();
		CharacterisesResponse response = getResponse(result, CharacterisesResponse.class);

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
		given(characteriseService.findById(characterise.getId())).willReturn(characterise);
		String path = "/characterises/" + characterise.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		CharacteriseResponse response = getResponse(result, CharacteriseResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, not(nullValue()));
		assertThat(response.getId(), equalTo(characterise.getId()));
		assertThat(response.getName(), equalTo(characterise.getName()));
	}

	@Test
	public void givenUnexistId_whenGetById_thenNotFound() throws Exception {
		given(characteriseService.findById(characterise.getId())).willReturn(null);
		String path = "/characterises/" + characterise.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}

	@Test
	public void whenCreate_thenSuccess() throws Exception {
		given(userService.findById(isA(Long.class))).willReturn(user);
		given(characteriseService.save(isA(Characterise.class))).willReturn(characterise);
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		CharacteriseResponse response = getResponse(result, CharacteriseResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.CREATED.value()));
		assertThat(response, not(nullValue()));
		assertThat(response.getUserId(), equalTo(characterise.getUserId()));
		assertThat(response.getName(), equalTo(characterise.getName()));
		assertThat(response.getDescription(), equalTo(characterise.getDescription()));
	}

	@Test
	public void givenUnexistUser_whenCreate_thenFailure() throws Exception {
		given(userService.findById(isA(Long.class))).willReturn(null);
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenExistName_whenCreate_thenFailure() throws Exception {
		given(userService.findById(isA(Long.class))).willReturn(user);
		given(characteriseService.findByName(user.getId(), characterise.getName())).willReturn(characterise);
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("name"));
		assertThat(errors.get("name").get(0), equalTo("is existed"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenUserIdNull_whenCreate_thenFailure() throws Exception {
		characteriseRequest.setUserId(null);
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("userId"));
		assertThat(errors.get("userId").get(0), equalTo("may not be null"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenNameNull_whenCreate_thenFailure() throws Exception {
		characteriseRequest.setName(null);
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("name"));
		assertThat(errors.get("name").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenNameBlank_whenCreate_thenFailure() throws Exception {
		characteriseRequest.setName("");
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("name"));
		assertThat(errors.get("name").get(0), equalTo("may not be empty"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenNameTooLong_whenCreate_thenFailure() throws Exception {
		characteriseRequest.setName(String.join("", Collections.nCopies(256, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("name"));
		assertThat(errors.get("name").get(0), equalTo("length must be between 0 and 255"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void givenDescriptionTooLong_whenCreate_thenFailure() throws Exception {
		characteriseRequest.setDescription(String.join("", Collections.nCopies(513, "a")));
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/characterises")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);
		HashMap<String, List<String>> errors = (HashMap<String, List<String>>) response.getErrors();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value()));
		assertThat(errors.keySet(), hasItem("description"));
		assertThat(errors.get("description").get(0), equalTo("length must be between 0 and 512"));
	}

	@Test
	public void whenUpdate_thenSuccess() throws Exception {
		given(characteriseService.findById(isA(Long.class))).willReturn(characterise);
		given(characteriseService.save(isA(Characterise.class))).willReturn(characterise);
		String path = "/characterises/" + characterise.getId();
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		CharacteriseResponse response = getResponse(result, CharacteriseResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
		assertThat(response, not(nullValue()));
		assertThat(response.getId(), equalTo(characterise.getId()));
		assertThat(response.getName(), equalTo(characterise.getName()));
		assertThat(response.getDescription(), equalTo(characterise.getDescription()));
	}

	@Test
	public void givenUnexistId_whenUpdate_thenSuccess() throws Exception {
		given(characteriseService.findById(characterise.getId())).willReturn(null);
		String path = "/characterises/" + characterise.getId();
		String jsonRequest = jsonMapper.writeValueAsString(characteriseRequest);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.put(path).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
				.andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}

	@Test
	public void whenDelete_thenSuccess() throws Exception {
		given(characteriseService.findById(characterise.getId())).willReturn(characterise);
		String path = "/characterises/" + characterise.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(path)).andReturn();

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.OK.value()));
	}

	@Test
	public void givenUnexistId_whenDelete_thenSuccess() throws Exception {
		given(characteriseService.findById(characterise.getId())).willReturn(null);
		String path = "/characterises/" + characterise.getId();

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(path)).andReturn();
		ErrorResponse response = getResponse(result, ErrorResponse.class);

		assertThat(result.getResponse().getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getErrors().toString(), equalTo("Not Found"));
	}
}

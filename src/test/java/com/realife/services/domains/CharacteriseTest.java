package com.realife.services.domains;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.realife.services.common.util.DateUtil;
import com.realife.services.models.characterises.CharacteriseResponse;

public class CharacteriseTest {

	private ModelMapper modelMapper = new ModelMapper();

	private static Validator validator;
	private Characterise characterise;

	@Before
	public void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		characterise = new Characterise();
		characterise.setName("A Test Skill");
		characterise.setLevel(Characterise.DEFAULT_LEVEL);
		characterise.setPoints(Characterise.DEFAULT_POINTS);
		characterise.setUserId(new Long(1));
		characterise.setCreatedAt(DateUtil.getUtcNow());
	}

	@Test
	public void whenNewCharacterise_thenValid() {
		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(0));
	}

	@Test
	public void givenUserIdNull_whenNewCharacterise_thenInvalid() {
		characterise.setUserId(null);

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be null"));
	}

	@Test
	public void givenNameNull_whenNewCharacterise_thenInvalid() {
		characterise.setName(null);

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}

	@Test
	public void givenNameBlank_whenNewCharacterise_thenInvalid() {
		characterise.setName("");

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}

	@Test
	public void givenNameTooLong_whenNewCharacterise_thenInvalid() {
		characterise.setName(String.join("", Collections.nCopies(256, "a")));

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 255"));
	}

	@Test
	public void givenPointsNull_whenNewCharacterise_thenInvalid() {
		characterise.setPoints(null);

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be null"));
	}

	@Test
	public void givenDescriptionTooLong_whenNewCharacterise_thenInvalid() {
		characterise.setDescription(String.join("", Collections.nCopies(513, "a")));

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 512"));
	}

	@Test
	public void givenCreatedAtNull_whenNewCharacterise_thenInvalid() {
		characterise.setCreatedAt(null);

		Set<ConstraintViolation<Characterise>> constraintViolations = validator.validate(characterise);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be null"));
	}

	@Test
	public void whenConvertToUserResponse_thenCorrect() {
		CharacteriseResponse response = modelMapper.map(characterise, CharacteriseResponse.class);

		assertThat(characterise.getId(), equalTo(response.getId()));
		assertThat(characterise.getName(), equalTo(response.getName()));
		assertThat(characterise.getCreatedAt(), equalTo(response.getCreatedAt()));
	}
}

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
import com.realife.services.models.users.UserResponse;

public class UserTest {

	private static Validator validator;
	private ModelMapper modelMapper = new ModelMapper();
	private User user;

	@Before
	public void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		user = new User();
		user.setId(new Long(1));
		user.setEmail("hung@gmail.com");
		user.setFirstName("Hung");
		user.setLastName("Nguyen");
		user.setPasswordDigest("123456");
		user.setCreatedAt(DateUtil.getUtcNow());
	}
	
	@Test
	public void whenNewUser_thenValid() {
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(0));
	}

	@Test
	public void givenFirstNameNull_whenNewUser_thenInvalid() {
		user.setFirstName(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}
	
	@Test
	public void givenFirstNameEmpty_whenNewUser_thenInvalid() {
		user.setFirstName("");
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}
	
	@Test
	public void givenFirstNameTooLong_whenNewUser_thenInvalid() {
		user.setFirstName(String.join("", Collections.nCopies(51, "a")));
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 50"));
	}
	
	@Test
	public void givenLastNameTooLong_whenNewUser_thenInvalid() {
		user.setLastName(String.join("", Collections.nCopies(51, "a")));
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 50"));
	}
	
	@Test
	public void givenEmailNull_whenNewUser_thenInvalid() {
		user.setEmail(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}
	
	@Test
	public void givenEmailEmpty_whenNewUser_thenInvalid() {
		user.setEmail("");
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}
	
	@Test
	public void givenEmailTooLong_whenNewUser_thenInvalid() {
		user.setEmail(String.join("", Collections.nCopies(256, "a")));
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 255"));
	}
	
	@Test
	public void givenPasswordDigestNull_whenNewUser_thenInvalid() {
		user.setPasswordDigest(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}
	
	@Test
	public void givenPasswordDigestEmpty_whenNewUser_thenInvalid() {
		user.setPasswordDigest("");
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be empty"));
	}
	
	@Test
	public void givenPasswordDigestTooLong_whenNewUser_thenInvalid() {
		user.setPasswordDigest(String.join("", Collections.nCopies(256, "a")));
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 255"));
	}
	
	@Test
	public void givenRememberDigestTooLong_whenNewUser_thenInvalid() {
		user.setRememberDigest(String.join("", Collections.nCopies(256, "a")));
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("length must be between 0 and 255"));
	}
	
	@Test
	public void givenCreatedAtNull_whenNewUser_thenInvalid() {
		user.setCreatedAt(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		assertThat(constraintViolations.size(), equalTo(1));
		assertThat(constraintViolations.iterator().next().getMessage(), equalTo("may not be null"));
	}

	@Test
	public void whenConvertToUserResponse_thenCorrect() {
		UserResponse response = modelMapper.map(user, UserResponse.class);

		assertThat(user.getId(), equalTo(response.getId()));
		assertThat(user.getEmail(), equalTo(response.getEmail()));
		assertThat(user.getCreatedAt(), equalTo(response.getCreatedAt()));
	}
}

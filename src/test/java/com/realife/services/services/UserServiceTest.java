package com.realife.services.services;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.realife.services.base.Application;
import com.realife.services.common.util.DateUtil;
import com.realife.services.domains.User;
import com.realife.services.models.users.UserFilterRequest;
import com.realife.services.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	private User user;

	@Before
	public void init() {
		if (user == null) {
			user = new User();
			user.setEmail("unittest" + Math.random() + "@gmail.com");
			user.setFirstName("Unit");
			user.setLastName("Test");
			user.setPasswordDigest("123456");
			user.setCreatedAt(DateUtil.getUtcNow());
			user.setUpdatedAt(DateUtil.getUtcNow());
			user = userRepository.save(user);
		}
	}

	@After
	public void dispose() {
		if (user != null) {
			userRepository.delete(user.getId());
			user = null;
		}
	}

	@Test
	public void whenFindAll_thenFound() {
		List<User> users = userService.findAll(new UserFilterRequest());

		assertThat(users, not(empty()));
	}

	@Test
	public void givenRightFirstName_whenFindAll_thenFound() {
		UserFilterRequest filter = new UserFilterRequest();
		filter.setFirstName(user.getFirstName());

		List<User> users = userService.findAll(filter);

		assertThat(users, not(empty()));
	}

	@Test
	public void givenWrongFirstName_whenFindAll_thenNotFound() {
		UserFilterRequest filter = new UserFilterRequest();
		filter.setFirstName("Wrong Name");

		List<User> users = userService.findAll(filter);

		assertThat(users, is(empty()));
	}

	@Test
	public void givenRightLastName_whenFindAll_thenFound() {
		UserFilterRequest filter = new UserFilterRequest();
		filter.setLastName(user.getLastName());

		List<User> users = userService.findAll(filter);

		assertThat(users, not(empty()));
	}

	@Test
	public void givenWrongLastName_whenFindAll_thenNotFound() {
		UserFilterRequest filter = new UserFilterRequest();
		filter.setLastName("Wrong Name");

		List<User> users = userService.findAll(filter);

		assertThat(users, is(empty()));
	}

	@Test
	public void givenCreatedAtMin_whenFindAll_thenFound() {
		UserFilterRequest filter = new UserFilterRequest();
		Date filterDate = DateUtil.addDays(user.getCreatedAt(), -1);
		filter.setCreatedAtMin(filterDate);

		List<User> users = userService.findAll(filter);

		assertThat(users, not(empty()));
	}

	@Test
	public void givenCreatedAtMax_whenFindAll_thenFound() {
		UserFilterRequest filter = new UserFilterRequest();
		Date filterDate = DateUtil.addDays(user.getCreatedAt(), 1);
		filter.setCreatedAtMax(filterDate);

		List<User> users = userService.findAll(filter);

		assertThat(users, not(empty()));
	}

	@Test
	public void givenUpdatedAtMin_whenFindAll_thenFound() {
		UserFilterRequest filter = new UserFilterRequest();
		Date filterDate = DateUtil.addDays(user.getUpdatedAt(), -1);
		filter.setUpdatedAtMin(filterDate);

		List<User> users = userService.findAll(filter);

		assertThat(users, not(empty()));
	}
	
	@Test
	public void givenUpdatedAtMax_whenFindAll_thenFound() {
		UserFilterRequest filter = new UserFilterRequest();
		Date filterDate = DateUtil.addDays(user.getUpdatedAt(), 1);
		filter.setUpdatedAtMax(filterDate);

		List<User> users = userService.findAll(filter);

		assertThat(users, not(empty()));
	}

	@Test
	public void givenExistId_whenFindById_thenFound() {
		User foundUser = userService.findById(user.getId());

		assertThat(foundUser, notNullValue());
	}

	@Test
	public void givenUnexistId_whenFindById_thenNotFound() {
		User foundUser = userService.findById(Long.MAX_VALUE);

		assertThat(foundUser, is(nullValue()));
	}

	@Test
	public void givenExistEmail_whenFindByEmail_thenFound() {
		User foundUser = userService.findByEmail(user.getEmail());

		assertThat(foundUser, notNullValue());
	}
	
	@Test
	public void givenUnexistEmail_whenFindByEmail_thenNotFound() {
		User foundUser = userService.findByEmail("unknownemail@gamil.com");
		
		assertThat(foundUser, is(nullValue()));
	}
}

package com.realife.services.services;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

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
			user.setEmail("hungtest" + Math.random() + "@gmail.com");
			user.setFirstName("Hung");
			user.setLastName("Nguyen");
			user.setPasswordDigest("123456");
			user.setCreatedAt(DateUtil.getUtcNow());
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
	public void whenFindById_thenFound() {
		User foundUser = userService.findById(user.getId());
		
		assertThat(foundUser, notNullValue());
	}
	
	@Test
	public void givenUnexistId_thenNotFound() {
		User foundUser = userService.findById(Long.MAX_VALUE);
		
		assertThat(foundUser, is(nullValue()));
	}
	
	@Test
	public void whenFindByEmail_thenFound() {
		User foundUser = userService.findByEmail(user.getEmail());
		
		assertThat(foundUser, notNullValue());
	}
	
	@Test
	public void givenUnexistEmail_thenNotFound() {
		User foundUser = userService.findByEmail("unknown" + Math.random() + "@gmail.com");
		
		assertThat(foundUser, is(nullValue()));
	}
}

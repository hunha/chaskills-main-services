//package com.realife.services.services;
//
//import static org.junit.Assert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//import java.util.Date;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.realife.services.base.Application;
//import com.realife.services.common.util.DateUtils;
//import com.realife.services.domains.Characterise;
//import com.realife.services.domains.User;
//import com.realife.services.models.characterises.CharacteriseFilterRequest;
//import com.realife.services.models.users.UserFilterRequest;
//import com.realife.services.repositories.UserRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class UserServiceTest {
//
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private UserRepository userRepository;
//
//	private User user;
//	private User anotherUser;
//
//	@Before
//	public void init() {
//		if (user == null) {
//			user = new User();
//			user.setEmail("user_test" + Math.random() + "@gmail.com");
//			user.setFirstName("Unit");
//			user.setLastName("Test");
//			user.setPasswordDigest("123456");
//			user.setCreatedAt(DateUtils.getUtcNow());
//			user.setUpdatedAt(DateUtils.getUtcNow());
//			user = userRepository.save(user);
//		}
//		
//		if (anotherUser == null) {
//			anotherUser = new User();
//			anotherUser.setEmail("another_user_test" + Math.random() + "@gmail.com");
//			anotherUser.setFirstName("Unit");
//			anotherUser.setLastName("Test");
//			anotherUser.setPasswordDigest("123456");
//			anotherUser.setCreatedAt(DateUtils.getUtcNow());
//			anotherUser.setUpdatedAt(DateUtils.getUtcNow());
//			anotherUser = userRepository.save(anotherUser);
//		}
//	}
//
//	@After
//	public void dispose() {
//		if (user != null) {
//			userRepository.delete(user.getId());
//			user = null;
//		}
//		
//		if (anotherUser != null) {
//			userRepository.delete(anotherUser.getId());
//			anotherUser = null;
//		}
//	}
//
//	@Test
//	public void whenFindAll_thenFound() {
//		List<User> users = userService.findAll(new UserFilterRequest());
//
//		assertThat(users, not(empty()));
//	}
//
//	@Test
//	public void givenRightFirstName_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setFirstName(user.getFirstName());
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, not(empty()));
//	}
//
//	@Test
//	public void givenWrongFirstName_whenFindAll_thenNotFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setFirstName("Wrong Name");
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, is(empty()));
//	}
//
//	@Test
//	public void givenRightLastName_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setLastName(user.getLastName());
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, not(empty()));
//	}
//
//	@Test
//	public void givenWrongLastName_whenFindAll_thenNotFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setLastName("Wrong Name");
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, is(empty()));
//	}
//
//	@Test
//	public void givenCreatedAtMin_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		Date filterDate = DateUtils.addDays(user.getCreatedAt(), -1);
//		filter.setCreatedAtMin(filterDate);
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, not(empty()));
//	}
//
//	@Test
//	public void givenCreatedAtMax_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		Date filterDate = DateUtils.addDays(user.getCreatedAt(), 1);
//		filter.setCreatedAtMax(filterDate);
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, not(empty()));
//	}
//
//	@Test
//	public void givenUpdatedAtMin_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		Date filterDate = DateUtils.addDays(user.getUpdatedAt(), -1);
//		filter.setUpdatedAtMin(filterDate);
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, not(empty()));
//	}
//	
//	@Test
//	public void givenUpdatedAtMax_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		Date filterDate = DateUtils.addDays(user.getUpdatedAt(), 1);
//		filter.setUpdatedAtMax(filterDate);
//
//		List<User> users = userService.findAll(filter);
//
//		assertThat(users, not(empty()));
//	}
//
//	@Test
//	public void givenRightPage_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setPage(1);
//		
//		List<User> users = userService.findAll(filter);
//		
//		assertThat(users, not(empty()));
//	}
//	
//	@Test
//	public void givenWrongPage_whenFindAll_thenNotFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setPage(100);
//		
//		List<User> users = userService.findAll(filter);
//		
//		assertThat(users, is(empty()));
//	}
//	
//	@Test
//	public void givenLimit_whenFindAll_thenFound() {
//		UserFilterRequest filter = new UserFilterRequest();
//		filter.setLimit(1);
//		
//		List<User> characterises = userService.findAll(filter);
//		
//		assertThat(characterises.size(), equalTo(1));
//	}
//	
//	@Test
//	public void givenExistId_whenFindById_thenFound() {
//		User foundUser = userService.findById(user.getId());
//
//		assertThat(foundUser, notNullValue());
//	}
//
//	@Test
//	public void givenUnexistId_whenFindById_thenNotFound() {
//		User foundUser = userService.findById(Long.MAX_VALUE);
//
//		assertThat(foundUser, is(nullValue()));
//	}
//
//	@Test
//	public void givenExistEmail_whenFindByEmail_thenFound() {
//		User foundUser = userService.findByEmail(user.getEmail());
//
//		assertThat(foundUser, notNullValue());
//	}
//	
//	@Test
//	public void givenUnexistEmail_whenFindByEmail_thenNotFound() {
//		User foundUser = userService.findByEmail("unknownemail@gamil.com");
//		
//		assertThat(foundUser, is(nullValue()));
//	}
//}

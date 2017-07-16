package com.realife.services.services;

import static org.junit.Assert.assertThat;

import java.util.List;

import static org.hamcrest.Matchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.realife.services.base.Application;
import com.realife.services.common.util.DateUtils;
import com.realife.services.domains.Characterise;
import com.realife.services.domains.User;
import com.realife.services.models.characterises.CharacteriseFilterRequest;
import com.realife.services.repositories.CharacteriseRepository;
import com.realife.services.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CharacteriseServiceTest {

	@Autowired
	private CharacteriseService characteriseService;
	@Autowired
	private CharacteriseRepository characteriseRepository;
	@Autowired
	private UserRepository userRepository;

	private User user;
	private Characterise characterise;
	private Characterise anotherCharacterise;

	@Before
	public void init() {
		if (user == null) {
			user = new User();
			user.setEmail("characterise_test" + Math.random() + "@gmail.com");
			user.setFirstName("Hung");
			user.setLastName("Nguyen");
			user.setPasswordDigest("123456");
			user.setCreatedAt(DateUtils.getUtcNow());
			user = userRepository.save(user);
		}

		if (characterise == null) {
			characterise = new Characterise();
			characterise.setId(null);
			characterise.setUserId(user.getId());
			characterise.setName("Skill Service" + Math.random());
			characterise.setLevel(Characterise.DEFAULT_LEVEL);
			characterise.setPoints(Characterise.DEFAULT_POINTS);
			characterise.setCreatedAt(DateUtils.getUtcNow());
			characterise = characteriseRepository.save(characterise);
		}

		if (anotherCharacterise == null) {
			anotherCharacterise = new Characterise();
			anotherCharacterise.setId(null);
			anotherCharacterise.setUserId(user.getId());
			anotherCharacterise.setName("Another Skill Service" + Math.random());
			anotherCharacterise.setLevel(Characterise.DEFAULT_LEVEL);
			anotherCharacterise.setPoints(Characterise.DEFAULT_POINTS);
			anotherCharacterise.setCreatedAt(DateUtils.getUtcNow());
			anotherCharacterise = characteriseRepository.save(anotherCharacterise);
		}
	}

	@After
	public void dispose() {
		if (user != null) {
			userRepository.delete(user.getId());
			user = null;
		}

		if (characterise != null) {
			characteriseRepository.delete(characterise);
			characterise = null;
		}

		if (anotherCharacterise != null) {
			characteriseRepository.delete(anotherCharacterise);
			anotherCharacterise = null;
		}
	}

	@Test
	public void whenFindAll_thenFound() {
		List<Characterise> characterises = characteriseService.findAll(new CharacteriseFilterRequest());

		assertThat(characterises, not(empty()));
	}

	@Test
	public void givenExistUserId_whenFindAll_thenFound() {
		CharacteriseFilterRequest filter = new CharacteriseFilterRequest();
		filter.setUserId(user.getId());

		List<Characterise> characterises = characteriseService.findAll(filter);

		assertThat(characterises, not(empty()));
	}

	@Test
	public void givenUnexistUserId_whenFindAl_thenNotFound() {
		CharacteriseFilterRequest filter = new CharacteriseFilterRequest();
		filter.setUserId(Long.MAX_VALUE);

		List<Characterise> characterises = characteriseService.findAll(filter);

		assertThat(characterises, is(empty()));
	}

	@Test
	public void givenRightPage_whenFindAll_thenFound() {
		CharacteriseFilterRequest filter = new CharacteriseFilterRequest();
		filter.setPage(1);

		List<Characterise> characterises = characteriseService.findAll(filter);

		assertThat(characterises, not(empty()));
	}

	@Test
	public void givenWrongPage_whenFindAll_thenNotFound() {
		CharacteriseFilterRequest filter = new CharacteriseFilterRequest();
		filter.setPage(100);

		List<Characterise> characterises = characteriseService.findAll(filter);

		assertThat(characterises, is(empty()));
	}

	@Test
	public void givenLimit_whenFindAll_thenFound() {
		CharacteriseFilterRequest filter = new CharacteriseFilterRequest();
		filter.setLimit(1);

		List<Characterise> characterises = characteriseService.findAll(filter);

		assertThat(characterises.size(), equalTo(1));
	}

	@Test
	public void givenExistUserId_whenFindByUserId_thenFound() {
		List<Characterise> characterises = characteriseService.findByUserId(user.getId());

		assertThat(characterises, not(empty()));
	}

	@Test
	public void givenUnexistUserId_whenFindByUserId_thenNotFound() {
		List<Characterise> characterises = characteriseService.findByUserId(Long.MAX_VALUE);

		assertThat(characterises, is(empty()));
	}

	@Test
	public void givenExistUserIdAndExistName_whenFindByName_thenFound() {
		Characterise foundCharacterise = characteriseService.findByName(user.getId(), characterise.getName());

		assertThat(foundCharacterise, not(nullValue()));
		assertThat(foundCharacterise.getId(), equalTo(characterise.getId()));
		assertThat(foundCharacterise.getName(), equalTo(characterise.getName()));
	}

	@Test
	public void givenExistUserIdAndUnexistName_whenFindByName_thenNotFound() {
		Characterise foundCharacterise = characteriseService.findByName(user.getId(), "Unexist Skill");

		assertThat(foundCharacterise, is(nullValue()));
	}

	@Test
	public void givenUnexistUserIdAndExistName_whenFindByName_thenNotFound() {
		Characterise foundCharacterise = characteriseService.findByName(Long.MAX_VALUE, characterise.getName());

		assertThat(foundCharacterise, is(nullValue()));
	}
}

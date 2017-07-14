package com.realife.services.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.realife.services.common.util.DateUtil;
import com.realife.services.domains.User;
import com.realife.services.models.users.UserFilterRequest;
import com.realife.services.repositories.UserRepository;
import com.realife.services.repositories.specs.SearchOperation;
import com.realife.services.repositories.specs.SearchSpecificationBuilder;
import com.realife.services.services.UserService;

import lombok.val;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<User> findAll(UserFilterRequest filter) {

		val spec = buildUserSpec(filter);
		val pageRequest = buildPageRequest(filter);
		Page<User> users = userRepository.findAll(spec, pageRequest);
		return users.getContent();
	}

	private Specification<User> buildUserSpec(UserFilterRequest filter) {
		val specBuilder = new SearchSpecificationBuilder<User>();
		if (!StringUtils.isBlank(filter.getFirstName())) {
			specBuilder.with("firstName", SearchOperation.EqualOrLike, filter.getFirstName());
		}
		if (!StringUtils.isBlank(filter.getLastName())) {
			specBuilder.with("lastName", SearchOperation.EqualOrLike, filter.getLastName());
		}
		if (filter.getCreatedAtMin() != null) {
			specBuilder.with("createdAt", SearchOperation.GreaterThanOrEqualTo, filter.getCreatedAtMin());
		}
		if (filter.getCreatedAtMax() != null) {
			specBuilder.with("createdAt", SearchOperation.LessThanOrEqualTo, filter.getCreatedAtMax());
		}
		if (filter.getUpdatedAtMin() != null) {
			specBuilder.with("updatedAt", SearchOperation.GreaterThanOrEqualTo, filter.getUpdatedAtMin());
		}
		if (filter.getUpdatedAtMax() != null) {
			specBuilder.with("updatedAt", SearchOperation.LessThanOrEqualTo, filter.getUpdatedAtMax());
		}

		return specBuilder.build();
	}

	@Override
	public User findById(Long id) {

		return userRepository.findById(id);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		if (user.getId() == null) {
			user.setCreatedAt(DateUtil.getUtcNow());
		} else {
			user.setUpdatedAt(DateUtil.getUtcNow());
		}

		return userRepository.save(user);
	}

	@Override
	public void delete(Long id) {
		userRepository.delete(id);
	}
}

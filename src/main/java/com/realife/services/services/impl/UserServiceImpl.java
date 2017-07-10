package com.realife.services.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.realife.services.domains.User;
import com.realife.services.repositories.UserRepository;
import com.realife.services.repositories.specs.SearchSpecificationBuilder;
import com.realife.services.services.UserService;
import com.realife.services.user.models.UserFilterRequest;

import lombok.val;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	UserRepository _userRepository;

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<User> findAll(UserFilterRequest filter) {

		val spec = _buildUserSpec(filter);
		val pageRequest = buildPageRequest(filter);
		Page<User> users = _userRepository.findAll(spec, pageRequest);
		return users.getContent();
	}

	private Specification<User> _buildUserSpec(UserFilterRequest filter) {
		val specBuilder = new SearchSpecificationBuilder<User>();
		if (!StringUtils.isBlank(filter.getEmail())) {
			specBuilder.with("email", ":", filter.getEmail());
		}

		return specBuilder.build();
	}

	@Override
	public User findById(Long id) {

		return _userRepository.findById(id);
	}
}

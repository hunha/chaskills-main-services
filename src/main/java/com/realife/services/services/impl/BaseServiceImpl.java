package com.realife.services.services.impl;

import org.springframework.data.domain.PageRequest;

import com.realife.services.domains.User;
import com.realife.services.user.models.UserFilterRequest;

public class BaseServiceImpl<T extends User> {

	protected PageRequest buildPageRequest(UserFilterRequest filter) {
		return new PageRequest(filter.getPage() - 1, filter.getLimit());
	}
}

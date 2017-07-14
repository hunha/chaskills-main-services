package com.realife.services.services.impl;

import org.springframework.data.domain.PageRequest;

import com.realife.services.domains.BaseDomain;
import com.realife.services.models.PagingFilterRequest;

public class BaseServiceImpl<T extends BaseDomain> {

	protected PageRequest buildPageRequest(PagingFilterRequest filter) {
		return new PageRequest(filter.getPage() - 1, filter.getLimit());
	}
}

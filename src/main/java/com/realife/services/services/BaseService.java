package com.realife.services.services;

import java.io.Serializable;

import com.realife.services.domains.BaseDomain;

public interface BaseService<T extends BaseDomain, ID extends Serializable> {
	
	T findById(Long id);
}

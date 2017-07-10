package com.realife.services.services;

import java.io.Serializable;

import com.realife.services.domains.User;

public interface BaseService<T extends User, ID extends Serializable> {
	
	T findById(Long id);
}

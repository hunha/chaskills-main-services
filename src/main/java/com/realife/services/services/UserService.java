package com.realife.services.services;


import java.util.List;

import com.realife.services.domains.User;
import com.realife.services.user.models.UserFilterRequest;


public interface UserService extends BaseService<User, Long> {

	List<User> findAll(UserFilterRequest filter);
	User findByEmail(String email);
	User save(User user);
	void delete(Long id);
}

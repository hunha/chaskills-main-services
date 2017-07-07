package com.realife.services.daos;

import java.util.List;

import com.realife.services.domains.User;
import com.realife.services.user.models.UserFilterRequest;

public interface UserDao {
	
	List<User> filter(UserFilterRequest model);
	
	User getById(int id);
	
	User getByEmail(String email);
}

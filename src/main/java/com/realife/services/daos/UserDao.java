package com.realife.services.daos;

import java.util.List;

import com.realife.services.dtos.User;
import com.realife.services.user.models.UserFilterModel;

public interface UserDao {
	
	List<User> filter(UserFilterModel model);
}

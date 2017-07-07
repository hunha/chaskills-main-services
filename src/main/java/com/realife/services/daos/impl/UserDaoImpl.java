package com.realife.services.daos.impl;

import java.util.List;

import com.realife.services.daos.UserDao;
import com.realife.services.domains.User;
import com.realife.services.user.models.UserFilterRequest;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public List<User> filter(UserFilterRequest model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}

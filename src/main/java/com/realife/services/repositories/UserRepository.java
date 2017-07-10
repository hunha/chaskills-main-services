package com.realife.services.repositories;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.stereotype.Repository;

import com.realife.services.domains.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long>, UserRepositoryCustom {

	@NotFound(action = NotFoundAction.IGNORE)
	User findByEmail(String email);
}
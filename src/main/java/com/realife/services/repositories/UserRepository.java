package com.realife.services.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.realife.services.domains.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, UserRepositoryCustom {
	
	User findByEmail(String email);
}
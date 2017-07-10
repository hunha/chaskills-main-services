package com.realife.services.repositories;

import java.io.Serializable;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.realife.services.domains.BaseDomain;
import com.realife.services.domains.User;

@SuppressWarnings("rawtypes")
public interface BaseRepository<T extends BaseDomain, ID extends Serializable>
		extends PagingAndSortingRepository<User, ID>, JpaSpecificationExecutor {

	@NotFound(action = NotFoundAction.IGNORE)
	T findById(ID id);
}

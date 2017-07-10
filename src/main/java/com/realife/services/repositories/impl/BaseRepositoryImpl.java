package com.realife.services.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realife.services.domains.BaseDomain;

public class BaseRepositoryImpl<T extends BaseDomain>{

	@PersistenceContext
	protected EntityManager entityManager;
}

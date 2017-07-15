package com.realife.services.repositories.specs;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification<T> implements Specification<T> {

	private SearchCriteria criteria;

	public SearchSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		switch (criteria.getOperation()) {
		case GreaterThanOrEqualTo:
			if (criteria.getValue() instanceof Date) {
				return cb.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()), (Date) criteria.getValue());
			}

			return cb.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		case LessThanOrEqualTo:
			if (criteria.getValue() instanceof Date) {
				return cb.lessThanOrEqualTo(root.<Date>get(criteria.getKey()), (Date) criteria.getValue());
			}

			return cb.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		case EqualOrLike:
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return cb.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
			}

			return cb.equal(root.get(criteria.getKey()), criteria.getValue());
		}

		return null;
	}
}

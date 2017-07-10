package com.realife.services.repositories.specs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.realife.services.domains.BaseDomain;

public class SearchSpecificationBuilder<T extends BaseDomain> {

	private final List<SearchCriteria> params;
	 
    public SearchSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }
    
    public SearchSpecificationBuilder<T> with(String key, SearchOperation operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
    
    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<T>> specs = new ArrayList<Specification<T>>();
        for (SearchCriteria param : params) {
            specs.add(new SearchSpecification<T>(param));
        }
 
        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        
        return result;
    }
}

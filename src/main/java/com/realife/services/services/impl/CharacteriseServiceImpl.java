package com.realife.services.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.realife.services.common.util.DateUtil;
import com.realife.services.domains.Characterise;
import com.realife.services.models.characterises.CharacteriseFilterRequest;
import com.realife.services.repositories.CharacteriseRepository;
import com.realife.services.repositories.specs.SearchOperation;
import com.realife.services.repositories.specs.SearchSpecificationBuilder;
import com.realife.services.services.CharacteriseService;

import lombok.val;

@Service
public class CharacteriseServiceImpl extends BaseServiceImpl<Characterise> implements CharacteriseService {

	@Autowired
	private CharacteriseRepository characteriseRepository;

	@Override
	@SuppressWarnings("unchecked")
	public List<Characterise> findAll(CharacteriseFilterRequest filter) {

		val spec = buildCharacteriseSpec(filter);
		val pageRequest = buildPageRequest(filter);
		Page<Characterise> characterises = characteriseRepository.findAll(spec, pageRequest);
		return characterises.getContent();
	}

	private Specification<Characterise> buildCharacteriseSpec(CharacteriseFilterRequest filter) {
		val specBuilder = new SearchSpecificationBuilder<Characterise>();
		if (filter.getUserId() != null) {
			specBuilder.with("userId", SearchOperation.EqualOrLike, filter.getUserId());
		}

		return specBuilder.build();
	}

	@Override
	public List<Characterise> findByUserId(Long userId) {

		return characteriseRepository.findByUserId(userId);
	}

	@Override
	public Characterise findById(Long id) {

		return characteriseRepository.findById(id);
	}
	
	@Override
	public Characterise findByName(Long userId, String name) {
		return characteriseRepository.findByName(userId, name);
	}

	@Override
	public Characterise save(Characterise characterise) {

		if (characterise.getId() == null) {
			characterise.setCreatedAt(DateUtil.getUtcNow());
		} else {
			characterise.setUpdatedAt(DateUtil.getUtcNow());
		}

		return characteriseRepository.save(characterise);
	}

	@Override
	public void delete(Long id) {
		characteriseRepository.delete(id);
	}

}

package com.realife.services.services;

import java.util.List;

import com.realife.services.domains.Characterise;
import com.realife.services.models.characterises.CharacteriseFilterRequest;

public interface CharacteriseService extends BaseService<Characterise, Long> {

	List<Characterise> findAll(CharacteriseFilterRequest filter);
	List<Characterise> findByUserId(Long userId);
	Characterise findByName(Long userId, String name);
	Characterise save(Characterise characterise);
	void delete(Long id);
}

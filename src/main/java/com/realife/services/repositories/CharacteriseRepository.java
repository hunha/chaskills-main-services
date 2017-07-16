package com.realife.services.repositories;

import java.util.List;

import com.realife.services.domains.Characterise;

public interface CharacteriseRepository extends BaseRepository<Characterise, Long> {

	List<Characterise> findByUserId(Long userId); 
	Characterise findByUserIdAndName(Long userId, String name);
}
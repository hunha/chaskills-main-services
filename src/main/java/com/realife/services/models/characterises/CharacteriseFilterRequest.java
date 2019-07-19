package com.realife.services.models.characterises;

import com.realife.services.models.PagingFilterRequest;

public class CharacteriseFilterRequest extends PagingFilterRequest {

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}

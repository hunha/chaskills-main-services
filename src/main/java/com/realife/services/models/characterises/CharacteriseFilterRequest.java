package com.realife.services.models.characterises;

import com.realife.services.models.PagingFilterRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacteriseFilterRequest extends PagingFilterRequest {

	private Long userId;
}

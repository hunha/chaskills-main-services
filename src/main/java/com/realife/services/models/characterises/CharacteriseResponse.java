package com.realife.services.models.characterises;

import java.util.Date;

import com.realife.services.models.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacteriseResponse extends BaseResponse {

	private Long id;
	private Long userId;
	private String name;
	private int level;
	private Long points;
	private String description;
	private Date createdAt;
	private Date updatedAt;
}

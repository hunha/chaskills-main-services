package com.realife.services.models.characterises;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.realife.services.models.BaseRequest;

public class CharacteriseRequest extends BaseRequest {

	@NotNull
	private Long userId;
	
	@NotBlank
	@Length(max = 255)
	private String name;
	
	@Length(max = 512)
	private String description;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

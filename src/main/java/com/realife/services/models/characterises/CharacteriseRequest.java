package com.realife.services.models.characterises;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.realife.services.models.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacteriseRequest extends BaseRequest {

	@NotNull
	private Long userId;
	
	@NotBlank
	@Length(max = 255)
	private String name;
	
	@Length(max = 512)
	private String description;
}

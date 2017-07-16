package com.realife.services.models.users;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.realife.services.models.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest extends BaseRequest {

	@NotBlank
	@Length(max = 50)
	private String firstName;
	
	@Length(max = 50)
	private String lastName;
	
	@NotBlank
	@Length(max = 255)
	private String email;
	
	@NotBlank
	@Length(max = 255)
	private String password;
	
	@Length(max = 255)
	private String rememberDigest;
}

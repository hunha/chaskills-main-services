package com.realife.services.user.models;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.realife.services.models.BaseRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest extends BaseRequest {

	@NotNull
	@Length(max = 50)
	private String firstName;
	
	@Length(max = 50)
	private String lastName;
	
	@NotNull
	@Length(max = 255)
	private String email;
	
	@NotNull
	@Length(max = 255)
	private String password;
	
	@Length(max = 255)
	private String rememberDigest;
	
	@NotNull
	private Date createdAt;
	
	private Date updatedAt;
}

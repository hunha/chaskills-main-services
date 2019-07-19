package com.realife.services.models.users;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.realife.services.models.BaseRequest;

public class UserRequest extends BaseRequest {

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRememberDigest() {
		return rememberDigest;
	}

	public void setRememberDigest(String rememberDigest) {
		this.rememberDigest = rememberDigest;
	}

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

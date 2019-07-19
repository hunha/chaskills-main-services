package com.realife.services.domains;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class User extends BaseDomain {

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
	private String passwordDigest;

	@Length(max = 255)
	private String rememberDigest;

	@NotNull
	private Date createdAt;

	private Date updatedAt;

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

	public String getPasswordDigest() {
		return passwordDigest;
	}

	public void setPasswordDigest(String passwordDigest) {
		this.passwordDigest = passwordDigest;
	}

	public String getRememberDigest() {
		return rememberDigest;
	}

	public void setRememberDigest(String rememberDigest) {
		this.rememberDigest = rememberDigest;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}

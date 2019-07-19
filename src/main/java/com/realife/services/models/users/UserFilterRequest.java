package com.realife.services.models.users;

import java.util.Date;

import com.realife.services.models.PagingFilterRequest;

public class UserFilterRequest extends PagingFilterRequest {

	private String firstName;
	private String lastName;
	private Date createdAtMin;
	private Date createdAtMax;
	private Date updatedAtMin;
	private Date updatedAtMax;
	
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
	public Date getCreatedAtMin() {
		return createdAtMin;
	}
	public void setCreatedAtMin(Date createdAtMin) {
		this.createdAtMin = createdAtMin;
	}
	public Date getCreatedAtMax() {
		return createdAtMax;
	}
	public void setCreatedAtMax(Date createdAtMax) {
		this.createdAtMax = createdAtMax;
	}
	public Date getUpdatedAtMin() {
		return updatedAtMin;
	}
	public void setUpdatedAtMin(Date updatedAtMin) {
		this.updatedAtMin = updatedAtMin;
	}
	public Date getUpdatedAtMax() {
		return updatedAtMax;
	}
	public void setUpdatedAtMax(Date updatedAtMax) {
		this.updatedAtMax = updatedAtMax;
	}
}

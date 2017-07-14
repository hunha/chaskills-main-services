package com.realife.services.models.users;

import java.util.Date;

import com.realife.services.models.PagingFilterRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilterRequest extends PagingFilterRequest {

	private String firstName;
	private String lastName;
	private Date createdAtMin;
	private Date createdAtMax;
	private Date updatedAtMin;
	private Date updatedAtMax;
}

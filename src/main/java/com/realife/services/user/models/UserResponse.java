package com.realife.services.user.models;

import java.util.Date;

import com.realife.services.models.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Date createdAt;
}

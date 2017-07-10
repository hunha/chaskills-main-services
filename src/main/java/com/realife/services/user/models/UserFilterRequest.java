package com.realife.services.user.models;

import com.realife.services.models.PagingFilterRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilterRequest extends PagingFilterRequest {

	private String email;
}

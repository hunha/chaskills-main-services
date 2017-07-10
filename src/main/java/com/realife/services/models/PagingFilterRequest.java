package com.realife.services.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingFilterRequest extends FilterRequest {

	private int page = 1;
	
	private int limit = 50;
}

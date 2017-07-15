package com.realife.services.models;

import lombok.Getter;

@Getter
public class PagingFilterRequest extends FilterRequest {

	public static int DEFAULT_PAGE = 1;
	public static int DEFAULT_LIMIT = 50;
	public static int MAX_LIMIT = 250;

	private int page = 1;
	private int limit = 50;

	public void setPage(int page) {
		if (page <= 0)
			page = DEFAULT_PAGE;

		this.page = page;
	}

	public void setLimit(int limit) {
		if (limit <= 0)
			limit = DEFAULT_LIMIT;
		if (limit > MAX_LIMIT)
			limit = MAX_LIMIT;

		this.limit = limit;
	}
}

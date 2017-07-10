package com.realife.services.common.utilities;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtility {
	
	public static Date getUtcNow() {
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		return Date.from(utc.toInstant());
	}
}

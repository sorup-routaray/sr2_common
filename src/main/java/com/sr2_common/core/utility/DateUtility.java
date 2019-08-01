package com.sr2_common.core.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public static Date getDateFromString(String inDate, String format) {
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(inDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static java.sql.Date getSQLdateFromUtilDate(Date utilDate) {
		if(utilDate == null) {
			return null;
		}
		return new java.sql.Date(utilDate.getTime());
	}
	
	public static Timestamp getCurrenttimestamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
}

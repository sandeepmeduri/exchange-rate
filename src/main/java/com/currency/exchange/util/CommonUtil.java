package com.currency.exchange.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Sandeep Meduri
 *
 */
public class CommonUtil {

	/**
	 * @param endDate
	 * @return
	 */
	public static String getStartDate(String endDate) {
		String startDate = "";
		try {
			LocalDate requestedDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
			startDate = requestedDate.minusDays(7).toString();
		} catch (DateTimeParseException e) {
			startDate = endDate;
		}
		return startDate;
	}
}

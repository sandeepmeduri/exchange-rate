package com.currency.exchange.util;

import org.springframework.stereotype.Component;

/**
 * @author Sandeep Meduri
 *
 */
@Component
public class Constants {

	public static final int FIVE = 5;
	public static final String ASCENDING = "ASCENDING";
	public static final String DESCENDING = "DESCENDING";
	public static final String CONSTANT = "CONSTANT";
	public static final String UNDEFINED = "UNDEFINED";
	public static final String EQUALS = "=";
	public static final String COLON = ":";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String SYMBOLS = "symbols";
	public static final String BASE = "base";
	public static final String BASE_CURRENCY = "baseCurrency";
	public static final String TARGET_CURRENCY = "targetCurrency";
	public static final String RATES = "rates";
	public static final String DATE = "date";
	public static final String START_AT = "start_at";
	public static final String END_AT = "end_at";
	public static final String PATH = "/error";
	public static final String EXCHANGE_URI = "https://api.exchangeratesapi.io/%s?base={base}&symbols={symbols}";
	public static final String EXCHANGE_HISTORY_URI = "https://api.exchangeratesapi.io/history?start_at={start_at}&end_at={end_at}&base={base}&symbols={symbols}";
}

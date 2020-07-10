package com.bk.exchange.dto;

import java.util.Map;

/**
 * @author Sandeep Meduri
 *
 */
public class ExchangeRateResponse {

	private String base;
	private String date;
	private Map<String,Object> rates;
	private String start_at;
	private String end_at;

	public ExchangeRateResponse() {
	}

	public ExchangeRateResponse(String base, String date, Map<String,Object> rates, String start_at, String end_at) {
		this.base = base;
		this.date = date;
		this.rates = rates;
		this.start_at = start_at;
		this.end_at = end_at;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<String,Object> getRates() {
		return rates;
	}

	public void setRates(Map<String,Object> rates) {
		this.rates = rates;
	}

	public String getStartAt() {
		return start_at;
	}

	public void setStart_at(String start_at) {
		this.start_at = start_at;
	}

	public String getEndAt() {
		return end_at;
	}

	public void setEnd_at(String end_at) {
		this.end_at = end_at;
	}
}

package com.bk.exchange.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Sandeep Meduri
 *
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) 
public class CurrencyRateDetails {

	public BigDecimal exchageRate;
	public BigDecimal average;
	public String trend;
	public LocalDate requestedDate;
	public String baseCurrency;
	public String targetCurrency;

	public CurrencyRateDetails(){	
	}
	
	public CurrencyRateDetails(BigDecimal exchageRate, BigDecimal averageRate, String trend) {
		this.exchageRate = exchageRate;
		this.average = averageRate;
		this.trend = trend;
	}

	public BigDecimal getExchageRate() {
		return exchageRate;
	}

	public void setExchageRate(BigDecimal exchageRate) {
		this.exchageRate = exchageRate;
	}

	public BigDecimal getAverage() {
		return average;
	}

	public void setAverage(BigDecimal average) {
		this.average = average;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
}

package com.bk.exchange.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author Sandeep Meduri
 *
 */
@Entity
public class ExchangeRate implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ExchangeRateId exchangeRateId;
	private BigDecimal rate;
	private BigDecimal average;
	private String trend;
	
	public ExchangeRate() {
	}

	public ExchangeRate(ExchangeRateId exchangeRateId, BigDecimal rate, BigDecimal average,
			String trend) {
		this.exchangeRateId = exchangeRateId;
		this.rate = rate;
		this.average = average;
		this.trend = trend;
	}

	public ExchangeRateId getExchangeRateId() {
		return exchangeRateId;
	}

	public void setExchangeRateId(ExchangeRateId exchangeRateId) {
		this.exchangeRateId = exchangeRateId;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
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
}

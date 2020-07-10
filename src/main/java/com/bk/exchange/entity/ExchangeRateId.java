package com.bk.exchange.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

/**
 * @author Sandeep Meduri
 *
 */
@Embeddable
public class ExchangeRateId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private LocalDate requestedDate;
	private String baseCurrency;
	private String targetCurrency;

	public ExchangeRateId() {
	}

	public ExchangeRateId(LocalDate requestedDate, String baseCurrency, String targetCurrency) {
		this.requestedDate=requestedDate;
		this.baseCurrency=baseCurrency;
		this.targetCurrency=targetCurrency;
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

package com.bk.exchange.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bk.exchange.entity.ExchangeRate;
import com.bk.exchange.entity.ExchangeRateId;

/**
 * @author Sandeep Meduri
 *
 */
public interface ExchangeRatesDAO extends CrudRepository<ExchangeRate, ExchangeRateId> {

	@Query("select e from ExchangeRate e where year(e.exchangeRateId.requestedDate) = ?1 and month(e.exchangeRateId.requestedDate) = ?2")
	List<ExchangeRate> getByYearAndMonth(int year, int month);
	
	@Query("select e from ExchangeRate e where e.exchangeRateId.requestedDate = ?1 ")
	List<ExchangeRate> getByDate(LocalDate date); 
}

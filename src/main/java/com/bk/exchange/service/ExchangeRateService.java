package com.bk.exchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bk.exchange.dao.ExchangeRatesDAO;
import com.bk.exchange.dto.CurrencyRateDetails;
import com.bk.exchange.dto.ExchangeRateResponse;
import com.bk.exchange.entity.ExchangeRate;
import com.bk.exchange.entity.ExchangeRateId;
import com.currency.exchange.util.CommonUtil;
import com.currency.exchange.util.Constants;

/**
 * @author Sandeep Meduri
 *
 */
@Service
public class ExchangeRateService {

	private RestTemplate restTemplate;
	private ExchangeRatesDAO exchangeRatesDAO;
	private ModelMapper mapper;

	@Autowired
	public ExchangeRateService(RestTemplate restTemplate, ExchangeRatesDAO exchangeRatesDAO,ModelMapper mapper) {
		this.restTemplate = restTemplate;
		this.exchangeRatesDAO = exchangeRatesDAO;
		this.mapper = mapper;
	}

	/**
	 * @param date
	 * @param base
	 * @param target
	 * @return
	 */
	public CurrencyRateDetails getExchageDetails(LocalDate date, String base, String target) {
		CurrencyRateDetails details = getDataFromAPI(date, base, target);
		persistSearchDetails(details, date, base, target);
		return details;
	}

	/**
	 * @param date
	 * @param base
	 * @param target
	 * @return
	 */
	private CurrencyRateDetails getDataFromAPI(LocalDate date, String base, String target) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put(Constants.BASE, base);
		uriVariables.put(Constants.SYMBOLS, target);
		ExchangeRateResponse exchangeRate = getExchageRate(date, entity, uriVariables);
		ExchangeRateResponse exchangeHistory = getExchangeHstry(exchangeRate.getDate(), entity, uriVariables);
		BigDecimal rate = new JSONObject(exchangeRate.getRates()).getBigDecimal(target);
		BigDecimal avg = getAverage(exchangeHistory, target);
		String trend = getTrend(exchangeHistory, target);
		return new CurrencyRateDetails(rate, avg, trend);
	}

	/**
	 * @param date
	 * @param entity
	 * @param uriVariables
	 * @return
	 */
	private ExchangeRateResponse getExchageRate(LocalDate date, HttpEntity<?> entity, Map<String, String> uriVariables) {
		String uri = UriComponentsBuilder.fromHttpUrl(String.format(Constants.EXCHANGE_URI, date.toString()))
				.buildAndExpand(uriVariables).toUriString();
		ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity,
				ExchangeRateResponse.class);
		return response.getBody();
	}

	/**
	 * @param date
	 * @param entity
	 * @param uriVariables
	 * @return
	 */
	private ExchangeRateResponse getExchangeHstry(String date, HttpEntity<?> entity, Map<String, String> uriVariables) {
		uriVariables.put(Constants.START_AT, CommonUtil.getStartDate(date));
		uriVariables.put(Constants.END_AT, date);
		ResponseEntity<ExchangeRateResponse> response = restTemplate.exchange(UriComponentsBuilder
				.fromHttpUrl(Constants.EXCHANGE_HISTORY_URI).buildAndExpand(uriVariables).encode().toUri(),
				HttpMethod.GET, entity, ExchangeRateResponse.class);
		return response.getBody();
	}

	/**
	 * @param response
	 * @param target
	 * @return
	 */
	private BigDecimal getAverage(ExchangeRateResponse response, String target) {
		List<BigDecimal> averageList = response.getRates().entrySet().stream()
				.filter(s -> !s.getKey().toString().equalsIgnoreCase(response.getEndAt()))
				.map(k -> new JSONObject(k.getValue().toString().replaceAll(Constants.EQUALS, Constants.COLON))
						.getBigDecimal(target))
				.collect(Collectors.toList());
		BigDecimal result = averageList.stream().reduce((a, b) -> a.add(b)).get()
				.divide(new BigDecimal(averageList.size()), Constants.FIVE, RoundingMode.HALF_UP);
		return result;
	}

	/**
	 * @param rates
	 * @param target
	 * @return
	 */
	public static String getTrend(ExchangeRateResponse rates, String target) {
		String result = Constants.UNDEFINED;
		List<BigDecimal> orderedRates = rates.getRates().entrySet().stream()
				.filter(s -> !s.getKey().toString().equalsIgnoreCase(rates.getEndAt()))
				.sorted(Map.Entry.<String, Object>comparingByKey())
				.map(k -> new JSONObject(k.getValue().toString().replaceAll(Constants.EQUALS, Constants.COLON))
						.getBigDecimal(target))
				.collect(Collectors.toList());

		if (isSorted(orderedRates, Constants.CONSTANT)) {
			result = Constants.CONSTANT;
		} else if (isSorted(orderedRates, Constants.ASCENDING)) {
			result = Constants.ASCENDING;
		} else if (isSorted(orderedRates, Constants.DESCENDING)) {
			result = Constants.DESCENDING;
		}
		return result;
	}

	/**
	 * @param rates
	 * @param order
	 * @return
	 */
	private static boolean isSorted(List<BigDecimal> rates, String order) {
		if (rates.isEmpty() || rates.size() == 1) {
			return true;
		}
		Iterator<BigDecimal> iter = rates.iterator();
		BigDecimal current, previous = iter.next();
		while (iter.hasNext()) {
			current = iter.next();
			if ((order == Constants.CONSTANT && previous.compareTo(current) != 0)
					|| (order == Constants.ASCENDING && previous.compareTo(current) > 0)
					|| (order == Constants.DESCENDING && previous.compareTo(current) < 0)) {
				return false;
			}
			previous = current;
		}
		return true;
	}

	/**
	 * @param details
	 * @param date
	 * @param base
	 * @param target
	 */
	private void persistSearchDetails(CurrencyRateDetails details, LocalDate date, String base, String target) {
		ExchangeRateId key = new ExchangeRateId(date, base, target);
		ExchangeRate entity = new ExchangeRate(key, details.getExchageRate(), details.getAverage(),
				details.getTrend());
		exchangeRatesDAO.save(entity);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public List<CurrencyRateDetails> getDailyHistoryForDate(LocalDate date) {
		List<ExchangeRate> result = exchangeRatesDAO.getByDate(date);	
		return result.stream().map(s -> mapper.map(s, CurrencyRateDetails.class)).collect(Collectors.toList());
	}

	/**
	 * @param year
	 * @param month
	 * @return
	 */
	public List<CurrencyRateDetails> getMonthlyHistory(int year, int month) {
		List<ExchangeRate> result = exchangeRatesDAO.getByYearAndMonth(year, month);
		return result.stream().map(s -> mapper.map(s, CurrencyRateDetails.class)).collect(Collectors.toList());
	}
}

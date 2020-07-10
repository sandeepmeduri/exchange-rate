package com.bk.exchange.rest;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.exchange.dto.CurrencyRateDetails;
import com.bk.exchange.dto.response.Response;
import com.bk.exchange.service.ExchangeRateService;
import com.currency.exchange.util.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sandeep Meduri
 *
 */
@RestController
@RequestMapping("/exchange-rate")
@Api(value = "ExchangeRate")
public class ExchangeRateController {

	private ExchangeRateService service;

	@Autowired
	ExchangeRateController(ExchangeRateService service) {
		this.service = service;
	}

	@ApiOperation(value = "Fetches required info for provided BaseCurrency and Target Currency", 
			response = CurrencyRateDetails.class, tags = "displayExchangeRates")
	@ApiResponses({ @ApiResponse(code = 200, message = "Fetched the exchange rates info successfully"),
			@ApiResponse(code = 404, message = "Exchange details not found") })
	@GetMapping(value = "/{date}/{baseCurrency}/{targetCurrency}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<CurrencyRateDetails> displayExchangeRates(
			@PathVariable(value = "date", required = true) @DateTimeFormat(pattern = Constants.DATE_FORMAT)  LocalDate date,
			@PathVariable(value = "baseCurrency", required = true) String base,
			@PathVariable(value = "targetCurrency", required = true) String target) {
		Response<CurrencyRateDetails> response = Response.ok();
		response.setPayload(service.getExchageDetails(date, base, target));
		return response;
	}

	@ApiOperation(value = "Fetches required info for provided BaseCurrency and Target Currency", 
			response = CurrencyRateDetails.class, tags = "displayMonthlyHistory")
	@ApiResponses({ @ApiResponse(code = 200, message = "Fetched the exchange rates info successfully"),
			@ApiResponse(code = 404, message = "Exchange details not found") })
	@GetMapping("/history/monthly/{yyyy}/{MM}")
	public Response<List<CurrencyRateDetails>> displayMonthlyHistory(
			@PathVariable(value = "yyyy", required = true) @DateTimeFormat(pattern = "yyyy") int year,
			@PathVariable(value = "MM", required = true) @DateTimeFormat(pattern = "MM") int month) {
		Response<List<CurrencyRateDetails>> response = Response.ok();
		response.setPayload(service.getMonthlyHistory(year, month));
		return response;

	}
	
	@GetMapping("/history/daily/{yyyy}/{MM}/{dd}")
	@ApiOperation(value = "Fetches required info for provided BaseCurrency and Target Currency", 
	response = CurrencyRateDetails.class, tags = "displayDailyHistory")
	@ApiResponses({ @ApiResponse(code = 200, message = "Fetched the exchange rates info successfully"),
			@ApiResponse(code = 404, message = "Exchange details not found") })
	public Response<List<CurrencyRateDetails>> displayDailyHistory(
			@PathVariable(value = "yyyy", required = true) @DateTimeFormat(pattern = "yyyy") int year,
			@PathVariable(value = "MM", required = true) @DateTimeFormat(pattern = "MM") int month,
			@PathVariable(value = "dd", required = true) @DateTimeFormat(pattern = "dd") int day) {	
		Response<List<CurrencyRateDetails>> response = Response.ok();
		response.setPayload(service.getDailyHistoryForDate(LocalDate.of(year, month, day)));
		return response;
	} 

}

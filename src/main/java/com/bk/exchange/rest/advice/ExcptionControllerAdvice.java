package com.bk.exchange.rest.advice;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bk.exchange.dto.response.ApiErrorResponse;
import com.bk.exchange.dto.response.Response;

/**
 * @author Sandeep Meduri
 *
 */
@ControllerAdvice
public class ExcptionControllerAdvice {

	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public HttpEntity<Response> handleException(Exception ex) {
		ApiErrorResponse apiResponse = new ApiErrorResponse();
	    apiResponse.setMessage(ex.getMessage());
	    apiResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	    apiResponse.setTimeStamp((LocalDateTime.now(ZoneOffset.UTC)));
	    return new ResponseEntity <Response> (new Response().addErrorMsgToResponse(apiResponse), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

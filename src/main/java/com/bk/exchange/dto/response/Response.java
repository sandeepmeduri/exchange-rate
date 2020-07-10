package com.bk.exchange.dto.response;

/**
 * @author Sandeep Meduri
 *
 * @param <T>
 */
public class Response<T> {

	public enum Status {
		OK, BAD_REQUEST, VALIDATION_EXCEPTION, EXCEPTION, NOT_FOUND
	}

	private Status status;
	private String description;
	private T payload;
	private Object errors;
	

	public Response() {
	}

	public static <T> Response<T> badRequest() {
		Response<T> response = new Response<>();
		response.setStatus(Status.BAD_REQUEST);
		return response;
	}

	public static <T> Response<T> ok() {
		Response<T> response = new Response<>();
		response.setStatus(Status.OK);
		response.setDescription("Success");
		return response;
	}

	public static <T> Response<T> validationException() {
		Response<T> response = new Response<>();
		response.setStatus(Status.VALIDATION_EXCEPTION);
		return response;
	}

	public static <T> Response<T> exception() {
		Response<T> response = new Response<>();
		response.setStatus(Status.EXCEPTION);
		return response;
	}

	public static <T> Response<T> notFound() {
		Response<T> response = new Response<>();
		response.setStatus(Status.NOT_FOUND);
		return response;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @param response
	 * @return
	 */
	public Response addErrorMsgToResponse(ApiErrorResponse response) {
		Response res = Response.exception();
		res.setErrors(response);
		res.setDescription("Error Occured while fetching data");
		return res;
	}

}

package com.sudhakar.web.exceptions;

public class RestAPIException extends RuntimeException {

	private final String message;
	
	public String getMessage() {
		return message;
	}
	
	public RestAPIException() {
		super();
		this.message = "";
	}
	
	public RestAPIException(String message) {
		super(message);
		this.message = message;
	}

	public RestAPIException(String message, Throwable cause) {
		super(message,cause);
		this.message = message;
	}
	
	
	public RestAPIException(Throwable cause) {
		super(cause);
		this.message = "";
	}

}

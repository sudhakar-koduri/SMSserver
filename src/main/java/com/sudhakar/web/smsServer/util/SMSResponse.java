package com.sudhakar.web.smsServer.util;

public class SMSResponse {

	public SMSResponse() {
		this.message = "";
		this.error = "";
	}
	
	public SMSResponse(String message, String error) {
		super();
		this.message = message;
		this.error = error;
	}
	
	// Getters / Setters 
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	@Override
	public String toString() {
		return "SMSResponse [message=" + message + ", error=" + error + "]";
	}

	private String message;
	private String error;
}

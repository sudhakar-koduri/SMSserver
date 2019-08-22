package com.sudhakar.web.exceptions;

public class AuthorizationFailedException extends RuntimeException {
	
	//private static final long serialVersionUID = -598090902010774538L;

	public AuthorizationFailedException() {
		super();
	}
	
	public AuthorizationFailedException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public AuthorizationFailedException(String message) {
		super(message);
	}
	
	public AuthorizationFailedException(Throwable cause) {
		super(cause);
	}
}

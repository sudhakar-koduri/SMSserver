package com.sudhakar.web.exceptions;

public class LimitExceedException extends RestAPIException {

	public LimitExceedException(String message) {
		super(message);
	}
}

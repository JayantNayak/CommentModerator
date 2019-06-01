package com.target.moderator.exception;

import org.springframework.http.HttpStatus;

public class ModeratorException extends Exception {
	private HttpStatus status;

	public ModeratorException(String errorMsg, HttpStatus status) {
		super(errorMsg);
		this.status = status;

	}

	public HttpStatus getStatus() {
		return status;
	}
}

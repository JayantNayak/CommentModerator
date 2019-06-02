package com.target.moderator.exception;

import org.springframework.http.HttpStatus;

import com.target.moderator.model.perspectiveapi.ErrorMessage;

public class ModeratorException extends Exception {

	private static final long serialVersionUID = -3746562963198843188L;
	private HttpStatus status;
	private ErrorMessage errorMsg;

	public ModeratorException(ErrorMessage errorMsg, HttpStatus status) {
		super(errorMsg.getErrorMessage().toString());
		this.errorMsg=errorMsg;
		this.status = status;

	}

	public HttpStatus getStatus() {
		return status;
	}

	public ErrorMessage getErrorMsg() {
		return errorMsg;
	}
	
}

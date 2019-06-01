package com.target.moderator.model.perspectiveapi;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessage {
	
	private List<String> errorMessage;

	public ErrorMessage(){
		errorMessage=new ArrayList<>();
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public ErrorMessage addErrorMessage(String errorMsg) {
		errorMessage.add(errorMsg);
		return this;
	}
	
	

}

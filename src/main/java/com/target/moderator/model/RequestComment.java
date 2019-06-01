package com.target.moderator.model;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Fields of a Request Comment")
public class RequestComment {
	private String comment;
	private String language;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}

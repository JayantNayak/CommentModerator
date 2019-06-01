package com.target.moderator.model;

import java.util.List;

public class RequestComment {
	private String comment;
	private List<String> languages;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
}

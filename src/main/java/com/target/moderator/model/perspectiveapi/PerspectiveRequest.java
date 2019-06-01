package com.target.moderator.model.perspectiveapi;

import java.util.List;

public class PerspectiveRequest {
	private Comment comment;
	private List<String> languages;
	private RequestedAttributes requestedAttributes;
	
	PerspectiveRequest(){}
	
	public PerspectiveRequest(String comment, List<String> languages){
		this.comment = new Comment(comment);
		this.languages = languages;
		this.requestedAttributes = new RequestedAttributes();
	}
	
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	public RequestedAttributes getRequestedAttributes() {
		return requestedAttributes;
	}
	public void setRequestedAttributes(RequestedAttributes requestedAttributes) {
		this.requestedAttributes = requestedAttributes;
	}
	

}

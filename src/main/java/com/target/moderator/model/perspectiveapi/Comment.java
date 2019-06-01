package com.target.moderator.model.perspectiveapi;

public class Comment {
	private String text;
	
	 Comment(){}
	 Comment(String txt){
		 this.text = txt;
	 }
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

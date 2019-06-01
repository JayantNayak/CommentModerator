package com.target.moderator.model;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Fields of a Response body")
public class ResponseComment {
	private double score;
	private String comment;
	private String feedback;
	public ResponseComment(){}
	public ResponseComment(double score){
		this.score = score;
		this.feedback = Feedback.getComment(score);
	}
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	

}

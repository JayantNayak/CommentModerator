package com.target.moderator.model;

public enum Feedback {

	CRITICAL("This cannot be posted."), MODERATE("Please improve your comment"), GOOD("Thank you for your comment.");

	private final String comment;

	Feedback(String comment) {
		this.comment = comment;
	}

	public static String getComment(double d) {
		String comment="";
	
		if (d >= 0.8) 				
			comment=Feedback.CRITICAL.comment;
		else if (0.75<= d && d < 0.8) 
			comment=Feedback.MODERATE.comment;
		else
			comment=Feedback.GOOD.comment;
		
		return comment;
	}

}

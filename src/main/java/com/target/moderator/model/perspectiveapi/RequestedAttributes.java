package com.target.moderator.model.perspectiveapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, 
getterVisibility = JsonAutoDetect.Visibility.NONE,
setterVisibility = JsonAutoDetect.Visibility.NONE, 
creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class RequestedAttributes {
	
	@JsonProperty(value="TOXICITY")
	private Toxicity toxicity;
	
	RequestedAttributes(){
		this.toxicity = new Toxicity();
	}

	public Toxicity getToxicity() {
		return toxicity;
	}

	public void setToxicity(Toxicity toxicity) {
		this.toxicity = toxicity;
	}
	
}

package com.target.moderator.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.moderator.model.RequestComment;
import com.target.moderator.model.ResponseComment;
import com.target.moderator.model.perspectiveapi.PerspectiveRequest;
import com.target.moderator.service.ModeratorService;

@Component
public class PerspectiveServiceImpl implements ModeratorService {
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment env;

	
	private  String url = null;
	
	

	public String intialiseUrl() {
		if(url == null){
			url = env.getProperty("perspectiveapi_url")+"?key="+env.getProperty("perspectiveapi_key");
		}
		return url;
	}

	@Override
	public ResponseComment analyzeComment(RequestComment requestComment) {

		if(url == null){
			intialiseUrl();
		}
	
		PerspectiveRequest pr = new PerspectiveRequest(requestComment.getComment(), requestComment.getLanguages());
		ResponseEntity<String> res = restTemplate.postForEntity(url, pr, String.class);
		System.out.println(res.getBody());

		ResponseComment rsp = constructResponse(res.getBody());
		if (rsp != null) {
			rsp.setComment(requestComment.getComment());
		}

		return rsp;
	}

	private ResponseComment constructResponse(String body) {
		ResponseComment rspComment = null;
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			JsonNode rsp = objectMapper.readTree(body.getBytes());
			double score = rsp.get("attributeScores").get("TOXICITY").get("summaryScore").get("value").asDouble();

			rspComment = new ResponseComment(score);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rspComment;
	}

}

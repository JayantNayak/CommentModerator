package com.target.moderator.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.moderator.exception.ModeratorException;
import com.target.moderator.model.RequestComment;
import com.target.moderator.model.ResponseComment;
import com.target.moderator.model.perspectiveapi.ErrorMessage;
import com.target.moderator.model.perspectiveapi.PerspectiveRequest;
import com.target.moderator.service.ModeratorService;

@Component
public class PerspectiveServiceImpl implements ModeratorService {

	private static final Logger logger = LoggerFactory.getLogger(PerspectiveServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private Environment env;

	private final String[] supportedLanguages = { "en", "fr", "es" };

	private String url = null;

	public String intialiseUrl() {
		if (url == null) {
			url = env.getProperty("perspectiveapi_url") + "?key=" + env.getProperty("perspectiveapi_key");
		}
		return url;
	}

	@Override
	public ResponseComment analyzeComment(RequestComment requestComment) throws ModeratorException {
		logger.debug("[Comment] " + requestComment);
		if (url == null) {
			intialiseUrl();
		}
		validateRequest(requestComment);
		PerspectiveRequest pr = new PerspectiveRequest(requestComment.getComment(), requestComment.getLanguage());
		ResponseEntity<String> res = null;
		ResponseComment rsp = null;
		try {
			res = restTemplate.postForEntity(url, pr, String.class);

			rsp = constructResponse(res.getBody());
			if (rsp != null) {
				rsp.setComment(requestComment.getComment());
			}

		} catch (Exception e) {
			logger.error("[ERROR] "+e.getMessage(), e);
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.addErrorMessage("Internal Server Error. Please check the logs");
			throw new ModeratorException(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return rsp;
	}

	private ResponseComment constructResponse(String body) throws IOException {
		ResponseComment rspComment = null;
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode rsp = objectMapper.readTree(body.getBytes());
		double score = rsp.get("attributeScores").get("TOXICITY").get("summaryScore").get("value").asDouble();

		rspComment = new ResponseComment(score);

		return rspComment;
	}

	@Override
	public String[] getSupportedLanguages() {

		return supportedLanguages;
	}

	@Override
	public boolean validateRequest(RequestComment requestComment) throws ModeratorException {
		String incomingLang = requestComment.getLanguage();

		ErrorMessage errorMsg = new ErrorMessage();
		boolean result = false;
		boolean isPresent = false;
		for (String supportedLang : getSupportedLanguages()) {
			if (supportedLang.equalsIgnoreCase(incomingLang)) {
				isPresent = true;
				break;
			}
		}
		if (!isPresent) {
			errorMsg.addErrorMessage("Language Not Supported.");
		}

		if (requestComment.getComment().trim().length() == 0) {
			errorMsg.addErrorMessage("Please provide a valid comment");
			result = true;
		}

		if (result || !isPresent) {
			throw new ModeratorException(errorMsg, HttpStatus.BAD_REQUEST);
		}
		return result;
	}

}

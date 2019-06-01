package com.target.moderator.service;

import com.target.moderator.exception.ModeratorException;
import com.target.moderator.model.RequestComment;
import com.target.moderator.model.ResponseComment;

public interface ModeratorService {
	public ResponseComment analyzeComment(RequestComment requestComment) throws ModeratorException;
	public boolean validateRequest(RequestComment requestComment) throws ModeratorException;
	public String[] getSupportedLanguages();

}

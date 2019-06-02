package com.target.moderator.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.target.moderator.exception.ModeratorException;
import com.target.moderator.model.RequestComment;
import com.target.moderator.model.ResponseComment;
import com.target.moderator.model.perspectiveapi.ErrorMessage;
import com.target.moderator.service.ModeratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
@Api(value = "Comment Evaluator", description = "Helps to evaluate your comments before going live")
public class ModeratorAPI {

	// Logger logger = LogManager.getLogger(ModeratorAPI.class);
	private static final Logger logger = LoggerFactory.getLogger(ModeratorAPI.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModeratorService moderatorService;

	@ApiOperation(value = "Evaluates your comment. Currently we only support three languages(en, fr, es).", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully posted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping("moderate")
	public ResponseEntity<?> evaluateComment(
			@ApiParam(value = "Comment body to be posted", required = true) @Valid @RequestBody RequestComment req) {

		logger.debug("[RequestComment] " + req);
		ResponseEntity<?> response = null;
		ResponseComment rc = null;
		try {
			rc = moderatorService.analyzeComment(req);
			response = new ResponseEntity<ResponseComment>(rc, HttpStatus.OK);
			logger.debug("[Response] " + response);
		} catch (ModeratorException e) {
			logger.error("[ERROR] " + e.getErrorMsg(),e);
			response = new ResponseEntity<ErrorMessage>(e.getErrorMsg(), e.getStatus());

		}

		return response;
	}

}

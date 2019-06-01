package com.target.moderator.api;

import javax.ws.rs.core.Response;

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
import com.target.moderator.service.ModeratorService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
public class ModeratorAPI {
	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	ModeratorService moderatorService; 
	
	
	@ApiOperation(value = "Evaluates your comment. Currently we only support three languages(en, fr, es).")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully posted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping("moderate")
	public ResponseEntity<?> evaluateComment(@RequestBody RequestComment req){
		ResponseEntity<?> response = null;
		ResponseComment  rc = null;
		try {
			
			rc=  moderatorService.analyzeComment(req);
			response = new ResponseEntity<ResponseComment>(rc,HttpStatus.OK);
			
			
			
		} catch (ModeratorException e) {
			
			System.out.println(e.getMessage());
			/*Response.status(e.getStatus())
			.entity(returnString).build();*/
			
			response = new ResponseEntity<String>(e.getMessage(),e.getStatus());
			
			
		}
		
		return response;
	}
		

}

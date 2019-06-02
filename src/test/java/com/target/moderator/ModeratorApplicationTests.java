package com.target.moderator;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.moderator.model.RequestComment;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class ModeratorApplicationTests {
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void getScoreSuccess()
	  throws Exception {
	 
	    RequestComment rq = new RequestComment();
	    rq.setComment("hii how are u");
	    rq.setLanguage("en");
	      		
	    mvc.perform( MockMvcRequestBuilders
	    	      .post("/moderate")
	    	      .content(asJsonString(rq))
	    	      .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isOk())
	    	      .andExpect(MockMvcResultMatchers.jsonPath("$.score").exists());
	}
	
	@Test
	public void getScoreFailed()
	  throws Exception {
	 
	    RequestComment rq = new RequestComment();
	    rq.setComment("hii how are u");
	    rq.setLanguage("somelanguage");
	      		
	    mvc.perform( MockMvcRequestBuilders
	    	      .post("/moderate")
	    	      .content(asJsonString(rq))
	    	      .contentType(MediaType.APPLICATION_JSON)
	    	      .accept(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isBadRequest());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}

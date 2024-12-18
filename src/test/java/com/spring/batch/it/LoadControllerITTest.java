package com.spring.batch.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.batch.Application;
import com.spring.batch.config.JwtUserTokenUtil;
import com.spring.batch.model.JwtRequest;
import com.spring.batch.model.SpringBatchUserRecords;
import com.spring.batch.model.UserDTO;
import com.spring.batch.repo.SpringBatchUserRecordsRepo;
import com.spring.batch.service.JwtUserDetailsService;
import lombok.var;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class LoadControllerITTest {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SpringBatchUserRecordsRepo springBatchUserRecordsRepo;

    @Test
    public void testLoad() throws Exception {
        UserDTO user = UserDTO.builder().username("test").password("test").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/register")
                        .contentType("application/json") // Specify the content type as JSON
                        .content(userJson)) // Add the JSON string as the request body
                .andExpect(status().isOk()) // Verify the response status
                .andExpect(jsonPath("$.username").value("test"));

        JwtRequest req = JwtRequest.builder().username("test").password("test").build();
        ObjectMapper reqMapper = new ObjectMapper();
        String reqJson = reqMapper.writeValueAsString(req);
        var mvcResult = mockMvc.perform(post("/authenticate")
                        .contentType("application/json") // Specify the content type as JSON
                        .content(reqJson)) // Add the JSON string as the request body
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();// Verify the response status


        String jwtToken = objectMapper.readTree(mvcResult).get("jwttoken").asText();


        var mvcResult1 = mockMvc.perform(get("/load")
                        .header("Authorization", "Bearer " + jwtToken) // Pass the token as a Bearer token
                        .contentType("application/json")) // Specify the content type as JSON
                .andExpect(status().isOk()) // Verify the response status
                .andReturn(); // Get the result

        List<SpringBatchUserRecords> allSpringBatchUserRecords = springBatchUserRecordsRepo.findAll(); // Fetch all records
        Assert.assertFalse(allSpringBatchUserRecords.isEmpty());
        Assert.assertEquals(5, allSpringBatchUserRecords.size());


    }
}

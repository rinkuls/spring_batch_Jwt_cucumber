package com.spring.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.batch.Application;
import com.spring.batch.config.JwtUserTokenUtil;
import com.spring.batch.model.UserDTO;
import com.spring.batch.service.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class JwtAuthenticationControllerTest {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSaveUser() throws Exception {
        UserDTO user = UserDTO.builder().username("test").password("test").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/register")
                        .contentType("application/json") // Specify the content type as JSON
                        .content(userJson)) // Add the JSON string as the request body
                .andExpect(status().isOk()) // Verify the response status
                .andExpect(jsonPath("$.username").value("test"));
    }
}

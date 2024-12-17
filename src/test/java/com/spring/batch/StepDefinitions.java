package com.spring.batch;

import com.spring.batch.model.JwtRequest;
import com.spring.batch.model.JwtResponse;
import com.spring.batch.model.UserDTO;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Log4j2

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
public class StepDefinitions {


    private static final HttpHeaders headers = new HttpHeaders();
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost";
    @Autowired
    EntityManager entityManager;
    @LocalServerPort
    private int port;

    @Before
    public void setUp() {

        var newUser = UserDTO.builder().username("test").password("password").build();
        var registerUrl = url + ":" + port + "/register";
        restTemplate.postForObject(registerUrl, newUser, UserDTO.class);


        var jwtRequest = JwtRequest.builder().username("test").password("password").build();

        var forJwtToken = url + ":" + port + "/authenticate";
        var tokenResponse = restTemplate.postForObject(forJwtToken, jwtRequest, JwtResponse.class);


        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tokenResponse.getJwttoken());
    }

    @After
    @Transactional
    public void tearDown() {
        entityManager.createQuery("DELETE FROM DAOUser ").executeUpdate();

    }


    @Given("I can send a new hello")
    public void i_can_send_a_new_hello() {
        var helloUrl = url + ":" + port + "/hello/13";
        var entity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(helloUrl, HttpMethod.GET, entity, String.class);
        assertNotNull(response);
    }

    @Given("I sending get to be say hello with hello id {int}")
    public void i_sending_get_to_be_say_hello_with_hello_id(Integer int1) {
        var helloUrl = url + ":" + port + "/hello/12";
        var entity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(helloUrl, HttpMethod.GET, entity, String.class);

        assertNotNull(response);

    }

    @Then("I should be able to see my newly created hello")
    public void i_should_be_able_to_see_my_newly_created_hello() {
        var helloUrl = url + ":" + port + "/hello/14";
        var entity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(helloUrl, HttpMethod.GET, entity, String.class);

        assertNotNull(response);
    }

    @Given("I can send a new request with username and password")
    public void i_can_send_a_new_request_with_username_and_password() {

        var newUser = UserDTO.builder().username("test").password("password").build();
        var registerUrl = url + ":" + port + "/register";
        var registerUser = restTemplate.postForObject(registerUrl, newUser, UserDTO.class);

        assertNotNull(registerUser);
        assertNotNull(registerUser.getPassword());
        assertNotNull(registerUser.getUsername());
    }

    @Given("I sending post to register with username abc and password abcd")
    public void i_sending_post_to_register_with_username_abc_and_password_abcd() {
        var newUser = UserDTO.builder().username("test").password("password").build();
        var registerUrl = url + ":" + port + "/register";
        var registerUser = restTemplate.postForObject(registerUrl, newUser, UserDTO.class);
        assertNotNull(registerUser);
        assertNotNull(registerUser.getPassword());
        assertNotNull(registerUser.getUsername());
    }

    @Then("I should be able to get response as ResponseEntity.ok")
    public void i_should_be_able_to_get_response_as_ResponseEntity_ok() {

        var newUser = UserDTO.builder().username("test").password("password").build();
        var registerUrl = url + ":" + port + "/register";
        var registerUser = restTemplate.postForObject(registerUrl, newUser, UserDTO.class);
        assertNotNull(registerUser);
        assertNotNull(registerUser.getPassword());
        assertNotNull(registerUser.getUsername());


    }


}

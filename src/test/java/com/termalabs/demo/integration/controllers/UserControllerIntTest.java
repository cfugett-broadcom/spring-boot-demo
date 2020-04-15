package com.termalabs.demo.integration.controllers;

import com.termalabs.demo.DemoApplication;
import com.termalabs.demo.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        classes = DemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class UserControllerIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void simpleTest() {
        int userId = 1;
        User user = this.testRestTemplate.getForObject("http://localhost:" + port + "/rest/users/" + userId, User.class);
        assertEquals(userId, user.getId());
    }

}

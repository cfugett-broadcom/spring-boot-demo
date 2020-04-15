package com.termalabs.demo.unit.services;

import com.termalabs.demo.models.User;
import com.termalabs.demo.services.UserServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServicesTest {

    @Autowired
    UserServices userServices;

    @Test
    public void testGetUserReturnsUserObject() {
        User user = userServices.getUser(1);
        assertEquals(1, user.getId());
        assertEquals(String.class, user.getFirstName().getClass());
        assertEquals(5, user.getFirstName().length());
        assertEquals(String.class, user.getLastName().getClass());
        assertEquals(5, user.getLastName().length());
    }
}

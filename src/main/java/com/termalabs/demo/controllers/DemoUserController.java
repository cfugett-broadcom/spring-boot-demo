package com.termalabs.demo.controllers;

import com.termalabs.demo.models.User;
import com.termalabs.demo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoUserController {

    @Autowired
    UserServices userServices;

    @GetMapping("/rest/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userServices.getUser(id);
    }
}

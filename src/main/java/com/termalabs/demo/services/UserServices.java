package com.termalabs.demo.services;

import com.termalabs.demo.models.User;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class UserServices {

    public User getUser(int id) {
        String randomFirstName = StringUtils.randomAlphanumeric(5);
        String randomLastName = StringUtils.randomAlphanumeric(5);
        return new User(id, randomFirstName, randomLastName);
    }
}

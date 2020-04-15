package com.termalabs.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoMvcController {

    @GetMapping("/")
    public String demo() {
        return "demo";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
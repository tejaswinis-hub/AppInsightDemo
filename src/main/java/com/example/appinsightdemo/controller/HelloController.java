package com.example.appinsightdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello,Java World!";
    }

    @GetMapping("/hello1")
    public String sayHelloPython() {
        return "Hello,Python World!";
    }

    @PostMapping("/hello2")
    public String sayHelloP() {
        return "Success";
    }

}
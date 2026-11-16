package com.microservice.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

    @GetMapping("/api/users/health")
    public String healthCheck(){
        return "user service is up and running";
    }

}

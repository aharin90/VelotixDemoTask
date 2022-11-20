package com.example.velotixdemo.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/health")
public class HealthController {

    @GetMapping
    public HttpEntity health() {
        return ResponseEntity.ok("Healthy");
    }
}

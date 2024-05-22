package com.example.rentify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthChechk {

    @GetMapping()
    ResponseEntity<String> healthCheck()
    {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }
}

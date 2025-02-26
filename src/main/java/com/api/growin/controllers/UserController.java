package com.api.growin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @PostMapping("/user")
    public ResponseEntity<String> seyHello() {
        return ResponseEntity.ok("Hello from User Controller!");
    }
}

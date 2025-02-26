package com.api.growin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.growin.models.User;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @PostMapping("/test")
    public ResponseEntity<String> seyHello(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getId().toString());
    }
}
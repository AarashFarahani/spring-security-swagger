package com.example.secureswagger.controller;

import com.example.secureswagger.model.Token;
import com.example.secureswagger.model.User;
import com.example.secureswagger.model.UserRequest;
import com.example.secureswagger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest user) {
        Token response = this.userService.findAuthorizedUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(response);
    }
}

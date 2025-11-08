package com.hyomyang.springaiboot.ai.controller;

import com.hyomyang.springaiboot.ai.dto.UserRequest;
import com.hyomyang.springaiboot.ai.dto.UserResponse;
import com.hyomyang.springaiboot.ai.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userServcie;

    public UserController(UserService userServcie) {
        this.userServcie = userServcie;
    }

    public ResponseEntity<UserResponse> create(@RequestBody UserRequest req) {
        return ResponseEntity.ok(userServcie.create(req));
    }

    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userServcie.getById(id));
    }
}

package com.hyomyang.springaiboot.ai.controller;

import com.hyomyang.springaiboot.ai.dto.response.ApiResponse;
import com.hyomyang.springaiboot.ai.dto.user.UserRequest;
import com.hyomyang.springaiboot.ai.dto.user.UserResponse;
import com.hyomyang.springaiboot.ai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create (@Valid @RequestBody UserRequest req) {
        UserResponse created = userService.create(req);
        return ResponseEntity.ok(ApiResponse.ok(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable Long id) {
        UserResponse user = userService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }
}

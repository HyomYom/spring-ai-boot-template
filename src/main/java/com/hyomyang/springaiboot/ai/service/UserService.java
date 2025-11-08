package com.hyomyang.springaiboot.ai.service;

import com.hyomyang.springaiboot.ai.domain.User;
import com.hyomyang.springaiboot.ai.dto.UserRequest;
import com.hyomyang.springaiboot.ai.dto.UserResponse;
import com.hyomyang.springaiboot.ai.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponse create(UserRequest req){
        User user = repo.save(new User(null, req.email(), req.name()));

        return new UserResponse(user.getId(), user.getEmail(), user.getName());

    }

    public UserResponse getById(Long id){
        User u = repo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        return new UserResponse(u.getId(), u.getEmail(), u.getName());
    }
}

package com.hyomyang.springaiboot.ai.service;

import com.hyomyang.springaiboot.ai.domain.User;
import com.hyomyang.springaiboot.ai.dto.user.UserRequest;
import com.hyomyang.springaiboot.ai.dto.user.UserResponse;
import com.hyomyang.springaiboot.ai.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserResponse create(UserRequest req){
        User user = repo.save(new User(null, req.email(), req.name(), req.role()));

        return UserResponse.of(user);

    }

    public UserResponse getById(Long id){
        User u = repo.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        return UserResponse.of(u, u.getRole());
    }
}

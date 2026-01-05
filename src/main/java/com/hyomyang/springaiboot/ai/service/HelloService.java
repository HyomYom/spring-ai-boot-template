package com.hyomyang.springaiboot.ai.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String great(String name){
     return "Hello " + name;
    }
}

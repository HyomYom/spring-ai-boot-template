package com.hyomyang.springaiboot.ai.security;

import java.util.List;
import java.util.Set;

public record UserPrincipal(Long userId, List<String> roles) {
}

package com.hyomyang.springaiboot.ai.repository;

import com.hyomyang.springaiboot.ai.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

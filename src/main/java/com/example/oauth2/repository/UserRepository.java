package com.example.oauth2.repository;

import com.example.oauth2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    User findById (String username); }


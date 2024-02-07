package com.atifzia.bikerental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atifzia.bikerental.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

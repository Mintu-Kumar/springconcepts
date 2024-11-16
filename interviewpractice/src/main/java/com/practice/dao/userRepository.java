package com.practice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entities.User;

public interface userRepository extends JpaRepository<User, Integer>{

}

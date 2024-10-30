package com.example.demo.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.common.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, String> {
}

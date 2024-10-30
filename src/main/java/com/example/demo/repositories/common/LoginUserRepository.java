package com.example.demo.repositories.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.common.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, String> {
}

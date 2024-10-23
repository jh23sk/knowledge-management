package com.example.demo.repositories.personal;

import com.example.demo.models.personal.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
	List<Category> findByOwnerId(String ownerId);
}
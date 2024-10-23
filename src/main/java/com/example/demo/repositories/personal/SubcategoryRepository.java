package com.example.demo.repositories.personal;

import com.example.demo.models.personal.Subcategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
	List<Subcategory> findByOwnerId(String ownerId);
}
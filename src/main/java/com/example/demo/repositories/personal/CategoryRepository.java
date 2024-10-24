package com.example.demo.repositories.personal;

import com.example.demo.models.personal.Category;
import com.example.demo.models.personal.TPersonalCategoryPK;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, TPersonalCategoryPK> {
	List<Category> findByOwnerId(String ownerId);
}
package com.example.demo.repositories.personal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.personal.Category;
import com.example.demo.models.personal.TPersonalCategoryPK;

@Repository
public interface CategoryRepository extends JpaRepository<Category, TPersonalCategoryPK> {
	List<Category> findByOwnerId(String ownerId);
}
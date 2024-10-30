package com.example.demo.repositories.personal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.personal.Subcategory;
import com.example.demo.models.personal.TPersonalSubcategoryPK;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, TPersonalSubcategoryPK> {
	List<Subcategory> findByOwnerId(String ownerId);
}
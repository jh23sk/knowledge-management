package com.example.demo.repositories.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.question.QuestionSubcategory;

@Repository
public interface QuestionSubcategoryRepository extends JpaRepository<QuestionSubcategory, String> {
}
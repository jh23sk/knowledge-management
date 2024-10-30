package com.example.demo.repositories.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.question.QuestionKnowledge;

@Repository
public interface QuestionKnowledgeRepository extends JpaRepository<QuestionKnowledge, String> {
}
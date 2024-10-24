package com.example.demo.repositories.personal;

import com.example.demo.models.personal.Knowledge;
import com.example.demo.models.personal.TPersonalKnowledgePK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, TPersonalKnowledgePK> {
}
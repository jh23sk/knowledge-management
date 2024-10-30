package com.example.demo.models.question;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_common_knowledge")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionKnowledge {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "category_id")
	private String categoryId;

	@Column(name = "subcategory_id")
	private String subcategoryId;

	@Column(name = "question")
	private String question;
	
	@Column(name = "question_date")
	private LocalDateTime questionDate;
	
	@Column(name = "question_user_id")
	private String questionUserId;
	
	@Column(name = "answer")
	private String answer;
	
	@Column(name = "answer_date")
	private LocalDateTime answerDate;
	
	@Column(name = "answer_user_id")
	private String answerUserId;

	@JsonIgnore 
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}

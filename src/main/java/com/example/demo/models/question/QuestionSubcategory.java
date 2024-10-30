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
@Table(name = "t_common_subcategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionSubcategory {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "creator_id")
	private String creatorId;

	@Column(name = "name", columnDefinition = "VARCHAR(300)")
	private String name;

	@Column(name = "category_id")
	private String categoryId;

	@JsonIgnore 
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}

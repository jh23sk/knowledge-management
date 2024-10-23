package com.example.demo.models.personal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_personal_knowledge")
@IdClass(KnowledgePK.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Knowledge {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Id
	@Column(name = "owner_id", nullable = false)
	private String ownerId;

	@Column(name = "category_id")
	private String categoryId;

	@Column(name = "subcategory_id")
	private String subcategoryId;

	@Column(name = "title", columnDefinition = "VARCHAR(300)")
	private String title;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@Column(name = "update_date")
	private LocalDateTime updateDate;
}

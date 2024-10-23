package com.example.demo.models.personal;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_personal_knowledge")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Knowledge {

	@Id
	private String id;

	@Column(name = "owner_id")
	private String ownerId;

	@Column(name = "category_id")
	private String categoryId;

	@Column(name = "subcategory_id")
	private String subcategoryId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "update_date")
	private LocalDateTime updateDate;
}

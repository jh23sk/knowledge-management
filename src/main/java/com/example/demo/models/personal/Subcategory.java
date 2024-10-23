package com.example.demo.models.personal;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_personal_subcategory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subcategory {

	@Id
	private String id;

	@Column(name = "owner_id")
	private String ownerId;

	@Column(name = "name")
	private String name;

	@Column(name = "category_id")
	private String categoryId;

	@Column(name = "update_date")
	private LocalDateTime updateDate;

}

package com.example.demo.models.personal;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_personal_category")
@IdClass(TPersonalCategoryPK.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Id
	@Column(name = "owner_id", nullable = false)
	private String ownerId;

	@Column(name = "name", columnDefinition = "VARCHAR(300)")
	private String name;

	@JsonIgnore
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}

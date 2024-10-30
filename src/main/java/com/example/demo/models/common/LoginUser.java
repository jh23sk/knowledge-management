package com.example.demo.models.common;

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
@Table(name = "t_login_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginUser {

	@Id
	@Column(name = "id", nullable = false)
	private String id;

	@Column(name = "name_sei")
	private String nameSei;
	
	@Column(name = "name_mei")
	private String nameMei;

	@Column(name = "password")
	private String password;

	@JsonIgnore 
	@Column(name = "update_date")
	private LocalDateTime updateDate;
}

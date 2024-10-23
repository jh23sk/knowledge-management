package com.example.demo.models.personal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchCondition {
	private String categoryId;
	private String subcategoryId;
	private String title;
	private String content;
}
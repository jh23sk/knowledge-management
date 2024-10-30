package com.example.demo.models.question;

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
	private String question;
	private String questionDateStt;
	private String questionDateEnd;
	private String questionUserName;
	private String answer;
	private String answerDateStt;
	private String answerDateEnd;
	private String answerUserName;
	private Boolean isAnswerd;
}
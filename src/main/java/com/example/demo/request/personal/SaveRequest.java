package com.example.demo.request.personal;

import java.util.List;

import com.example.demo.models.personal.Category;
import com.example.demo.models.personal.Knowledge;
import com.example.demo.models.personal.SearchCondition;
import com.example.demo.models.personal.Subcategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveRequest {
	private List<Category> categories;
	private List<Subcategory> subcategories;
	private List<Knowledge> knowledges;
	private SearchCondition searchCondition;
}

package com.example.demo.controllers.question;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.question.QuestionCategory;
import com.example.demo.models.question.QuestionKnowledge;
import com.example.demo.models.question.QuestionSubcategory;
import com.example.demo.models.question.SearchCondition;
import com.example.demo.services.question.QuestionCategoryService;
import com.example.demo.services.question.QuestionKnowledgeService;
import com.example.demo.services.question.QuestionSubcategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/question")
public class QuestionDataController {

	private final QuestionCategoryService categoryService;
	private final QuestionSubcategoryService subcategoryService;
	private final QuestionKnowledgeService knowledgeService;

	public QuestionDataController(QuestionCategoryService categoryService,
			QuestionSubcategoryService subcategoryService,
			QuestionKnowledgeService knowledgeService) {
		this.categoryService = categoryService;
		this.subcategoryService = subcategoryService;
		this.knowledgeService = knowledgeService;
	}

	/**
	 * カテゴリー/サブカテゴリーリストをDBから検索します。
	 */
	@GetMapping("/searchCategory")
	public ResponseEntity<Map<String, Object>> getCategory() {

		// ログインユーザー情報を取得
		//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//		String loginUserId = authentication.getName();

		// 検索
		List<QuestionCategory> categories = categoryService.getAllCategories();
		List<QuestionSubcategory> subcategories = subcategoryService.getAllSubcategories();

		Map<String, Object> response = new HashMap<>();
		response.put("categories", categories);
		response.put("subcategories", subcategories);
		return ResponseEntity.ok(response);
	}

	/**
	 * ナレッジリストをDBから検索します。
	 */
	@PostMapping("/searchKnowledge")
	public ResponseEntity<Map<String, Object>> searchKnowledge(@RequestParam String searchCondition) {
		try {
			//			// ログインユーザー情報を取得
			//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			//			String loginUserId = authentication.getName();

			// JSONから変換
			ObjectMapper objectMapper = new ObjectMapper();
			//objectMapper.registerModule(new JavaTimeModule());
			SearchCondition searchCond = objectMapper.readValue(searchCondition, SearchCondition.class);

			// 検索
			List<QuestionKnowledge> knowledges = knowledgeService.searchKnowledges(searchCond);

			Map<String, Object> response = new HashMap<>();
			response.put("knowledges", knowledges);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", "検索に失敗しました。"));
		}
	}

	/**
	 * ナレッジをDBに保存します。
	 */
	@PostMapping("/question")
	@Transactional
	public ResponseEntity<String> save(
			@RequestParam String categories,
			@RequestParam String subcategories,
			@RequestParam String postContent) {

		try {
			// ログインユーザー情報を取得
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String loginUserId = authentication.getName();

			// JSONから変換
			ObjectMapper objectMapper = new ObjectMapper();
			List<QuestionCategory> categoryList = objectMapper.readValue(categories,
					new TypeReference<List<QuestionCategory>>() {
					});
			List<QuestionSubcategory> subcategoryList = objectMapper.readValue(subcategories,
					new TypeReference<List<QuestionSubcategory>>() {
					});
			QuestionKnowledge knowledge = objectMapper.readValue(postContent, QuestionKnowledge.class);

			// 任意入力の項目がブランクの場合、nullに変換
			knowledge.setCategoryId(StringUtils.trimToNull(knowledge.getCategoryId()));
			knowledge.setSubcategoryId(StringUtils.trimToNull(knowledge.getSubcategoryId()));
			knowledge.setQuestion(StringUtils.trimToNull(knowledge.getQuestion()));

			// insert
			categoryService.saveCategories(categoryList, loginUserId);
			subcategoryService.saveSubcategories(subcategoryList, loginUserId);
			knowledgeService.saveKnowledges(knowledge, loginUserId);

			return ResponseEntity.ok("データ保存成功");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("データ保存失敗");
		}
	}

	/**
	 * ナレッジをに回答情報を設定し、更新します。
	 */
	@PostMapping("/answer")
	@Transactional
	public ResponseEntity<String> updateKnowledge(@RequestParam String postContent) {
		try {
		// ログインユーザー情報を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = authentication.getName();

		// JSONから変換
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		QuestionKnowledge knowledge = objectMapper.readValue(postContent, QuestionKnowledge.class);
		
		// update
		knowledgeService.updateQuestionKnowledge(knowledge, loginUserId);
		
		return ResponseEntity.ok("データ保存成功");
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("データ保存失敗");
		}
	}
}

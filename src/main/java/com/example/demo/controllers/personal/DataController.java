package com.example.demo.controllers.personal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.personal.Category;
import com.example.demo.models.personal.Knowledge;
import com.example.demo.models.personal.SearchCondition;
import com.example.demo.models.personal.Subcategory;
import com.example.demo.services.personal.CategoryService;
import com.example.demo.services.personal.KnowledgeService;
import com.example.demo.services.personal.SubcategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/personal")
public class DataController {

	private final CategoryService categoryService;
	private final SubcategoryService subcategoryService;
	private final KnowledgeService knowledgeService;

	public DataController(CategoryService categoryService,
			SubcategoryService subcategoryService,
			KnowledgeService knowledgeService) {
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginUserId = authentication.getName();

		// 検索
		List<Category> categories = categoryService.getCategoriesByOwnerId(loginUserId);
		List<Subcategory> subcategories = subcategoryService.getSubcategoriesByOwnerId(loginUserId);

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
			// ログインユーザー情報を取得
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String loginUserId = authentication.getName();

			// JSONから変換
			ObjectMapper objectMapper = new ObjectMapper();
			SearchCondition searchCond = objectMapper.readValue(searchCondition, SearchCondition.class);

			// 検索
			List<Knowledge> knowledges = knowledgeService.searchKnowledges(searchCond, loginUserId);

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
	 * ナレッジリストをDBに保存します。
	 */
	@PostMapping("/save")
	@Transactional
	public ResponseEntity<String> save(
			@RequestParam String categories,
			@RequestParam String subcategories,
			@RequestParam String knowledges,
			@RequestParam String searchCondition) {

		try {
			// ログインユーザー情報を取得
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String loginUserId = authentication.getName();

			// JSONから変換
			ObjectMapper objectMapper = new ObjectMapper();
			List<Category> categoryList = objectMapper.readValue(categories,
					new TypeReference<List<Category>>() {
					});
			List<Subcategory> subcategoryList = objectMapper.readValue(subcategories,
					new TypeReference<List<Subcategory>>() {
					});
			List<Knowledge> knowledgeList = objectMapper.readValue(knowledges,
					new TypeReference<List<Knowledge>>() {
					});
			SearchCondition searchCond = objectMapper.readValue(searchCondition, SearchCondition.class);

			// 画面に表示中のデータをいったんdelete
			categoryService.deleteCategoriesByOwnerId(loginUserId);
			subcategoryService.deleteSubcategoriesByOwnerId(loginUserId);
			knowledgeService.deleteKnowledgesBySearchCond(searchCond, loginUserId);

			// 任意入力の項目がブランクの場合、nullに変換
			for (Knowledge knowledge : knowledgeList) {
				knowledge.setCategoryId(StringUtils.trimToNull(knowledge.getCategoryId()));
				knowledge.setSubcategoryId(StringUtils.trimToNull(knowledge.getSubcategoryId()));
				knowledge.setTitle(StringUtils.trimToNull(knowledge.getTitle()));
				knowledge.setContent(StringUtils.trimToNull(knowledge.getContent()));
			}

			// 画面に表示中のデータをinsert
			categoryService.saveCategories(categoryList, loginUserId);
			subcategoryService.saveSubcategories(subcategoryList, loginUserId);
			knowledgeService.saveKnowledges(knowledgeList, loginUserId);

			return ResponseEntity.ok("データ保存成功");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("データ保存失敗");
		}
	}
}

package com.example.demo.controllers.personal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.personal.Category;
import com.example.demo.models.personal.Knowledge;
import com.example.demo.models.personal.SearchCondition;
import com.example.demo.models.personal.Subcategory;
import com.example.demo.request.personal.SaveRequest;
import com.example.demo.services.personal.CategoryService;
import com.example.demo.services.personal.KnowledgeService;
import com.example.demo.services.personal.SubcategoryService;

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

	@GetMapping("/searchCategory")
	public ResponseEntity<Map<String, Object>> getCategory() {

		String loginUserId = "test_user";

		List<Category> categories = categoryService.getCategoriesByOwnerId(loginUserId);
		List<Subcategory> subcategories = subcategoryService.getSubcategoriesByOwnerId(loginUserId);

		Map<String, Object> response = new HashMap<>();
		response.put("categories", categories);
		response.put("subcategories", subcategories);
		return ResponseEntity.ok(response);
	}

	/**
	 * ナレッジリストをDBから検索します。
	 * 
	 * @return List<Knowledge> 検索条件に合致するナレッジリスト
	 */
	@PostMapping("/searchKnowledge")
	public ResponseEntity<Map<String, Object>> searchKnowledge(@RequestBody SearchCondition searchCond) {
		try {
			String loginUserId = "test_user";

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

	@PostMapping("/save")
	@Transactional
	public ResponseEntity<String> saveData(@RequestBody SaveRequest saveRequest) {
		try {
			String loginUserId = "test_user";
	
			// いったん画面に表示中のデータをdelete
			categoryService.deleteCategoriesByOwnerId(loginUserId);
			subcategoryService.deleteSubcategoriesByOwnerId(loginUserId);
			SearchCondition searchCond = saveRequest.getSearchCondition();
			knowledgeService.deleteKnowledgesBySearchCond(searchCond, loginUserId);
			
			// 任意入力の項目がブランクの場合、nullに変換
			List<Knowledge> knowledges = saveRequest.getKnowledges();
			for (Knowledge knowledge : knowledges) {
				knowledge.setCategoryId(StringUtils.trimToNull(knowledge.getCategoryId()));
				knowledge.setSubcategoryId(StringUtils.trimToNull(knowledge.getSubcategoryId()));
				knowledge.setTitle(StringUtils.trimToNull(knowledge.getTitle()));
				knowledge.setContent(StringUtils.trimToNull(knowledge.getContent()));
			}
	
			// 画面に表示中のデータをinsert
			categoryService.saveCategories(saveRequest.getCategories(), loginUserId);
			subcategoryService.saveSubcategories(saveRequest.getSubcategories(), loginUserId);
			knowledgeService.saveKnowledges(knowledges, loginUserId);
	
			return ResponseEntity.ok("データ保存成功");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("データ保存失敗");
		}
	}
}

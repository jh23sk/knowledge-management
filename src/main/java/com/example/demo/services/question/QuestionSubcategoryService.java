package com.example.demo.services.question;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.question.QuestionSubcategory;
import com.example.demo.repositories.question.QuestionSubcategoryRepository;

@Service
public class QuestionSubcategoryService {

	private final QuestionSubcategoryRepository subcategoryRepository;

	public QuestionSubcategoryService(QuestionSubcategoryRepository subcategoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
	}

	/**
	 * カテゴリーリストをDBから検索します。
	 * 対象：全て
	 * 
	 * @return List<Category> カテゴリーリスト
	 */
	public List<QuestionSubcategory> getAllSubcategories() {
		return subcategoryRepository.findAll();
	}
	
	/**
	 * サブカテゴリーリストをDBから削除します。
	 * 対象：全て
	 * 
	 * @param loginUserId ログインユーザーID
	 */
	public void deleteAllSubcategories() {
		List<QuestionSubcategory> subcategories = subcategoryRepository.findAll();
		if (!subcategories.isEmpty()) {
			subcategoryRepository.deleteAll(subcategories);
		}
	}

	/**
	 * サブカテゴリーリストをDBに保存します。
	 * t_personal_subcategory.owner_idにログインユーザーID、
	 * t_personal_subcategory.update_dateにシステム日時を設定します。
	 * 
	 * @param subcategories 保存するサブカテゴリーリスト
	 * @param loginUserId ログインユーザーID
	 * @throws ConstraintViolationException 制約違反の場合
	 */
	public void saveSubcategories(List<QuestionSubcategory> subcategories, String loginUserId) {
		for (QuestionSubcategory subcategory : subcategories) {
			// creatorIdにログインユーザーIDを設定
			subcategory.setCreatorId(loginUserId);
			// updateDateにシステム日時を設定
			subcategory.setUpdateDate(LocalDateTime.now());
		}
		subcategoryRepository.saveAll(subcategories);
	}
}

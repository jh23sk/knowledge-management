package com.example.demo.services.question;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.question.QuestionCategory;
import com.example.demo.repositories.question.QuestionCategoryRepository;

@Service
public class QuestionCategoryService {

	private final QuestionCategoryRepository categoryRepository;

	public QuestionCategoryService(QuestionCategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 * カテゴリーリストをDBから検索します。
	 * 対象：全て
	 * 
	 * @return List<Category> カテゴリーリスト
	 */
	public List<QuestionCategory> getAllCategories() {
		return categoryRepository.findAll();
	}

//	/**
//	 * カテゴリーリストをDBから検索します。
//	 * 対象：t_personal_category.owner_idとログインユーザーIDが一致するデータ
//	 * 
//	 * @param loginUserId ログインユーザーID
//	 * @return List<Category> カテゴリーリスト
//	 */
//	public List<Category> getCategoriesByOwnerId(String loginUserId) {
//		return categoryRepository.findByOwnerId(loginUserId);
//	}

	/**
	 * カテゴリーリストをDBから削除します。
	 * 対象：全て
	 * 
	 * @param loginUserId ログインユーザーID
	 */
	public void deleteAllCategories() {
		List<QuestionCategory> categories = categoryRepository.findAll();
		if (!categories.isEmpty()) {
			categoryRepository.deleteAll(categories);
		}
	}

	/**
	 * カテゴリーリストをDBに保存します。
	 * t_personal_category.owner_idにログインユーザーID、
	 * t_personal_category.update_dateにシステム日時を設定します。
	 * 
	 * @param categories 保存するカテゴリーリスト
	 * @param loginUserId ログインユーザーID
	 * @throws ConstraintViolationException 制約違反の場合
	 */
	public void saveCategories(List<QuestionCategory> categories, String loginUserId) {
		for (QuestionCategory category : categories) {
			// creatorIdにログインユーザーIDを設定
			category.setCreatorId(loginUserId);
			// updateDateにシステム日時を設定
			category.setUpdateDate(LocalDateTime.now());
		}
		categoryRepository.saveAll(categories);
	}
}

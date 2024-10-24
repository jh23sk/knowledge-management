package com.example.demo.services.personal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.personal.Category;
import com.example.demo.repositories.personal.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 * カテゴリーリストをDBから検索します。
	 * 対象：t_personal_category.owner_idとログインユーザーIDが一致するデータ
	 * 
	 * @param loginUserId ログインユーザーID
	 * @return List<Category> カテゴリーリスト
	 */
	public List<Category> getCategoriesByOwnerId(String loginUserId) {
		return categoryRepository.findByOwnerId(loginUserId);
	}

	/**
	 * カテゴリーリストをDBから削除します。
	 * 対象：t_personal_category.owner_idとログインユーザーIDが一致するデータ
	 * 
	 * @param loginUserId ログインユーザーID
	 */
	public void deleteCategoriesByOwnerId(String ownerId) {
		List<Category> categories = getCategoriesByOwnerId(ownerId);
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
	public void saveCategories(List<Category> categories, String loginUserId) {
		for (Category category : categories) {
			// ownerIdにログインユーザーIDを設定
			category.setOwnerId(loginUserId);
			// updateDateにシステム日時を設定
			category.setUpdateDate(LocalDateTime.now());
		}
		categoryRepository.saveAll(categories);
	}
}

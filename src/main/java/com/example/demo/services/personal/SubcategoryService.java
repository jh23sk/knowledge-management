package com.example.demo.services.personal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.personal.Subcategory;
import com.example.demo.repositories.personal.SubcategoryRepository;

@Service
public class SubcategoryService {

	private final SubcategoryRepository subcategoryRepository;

	public SubcategoryService(SubcategoryRepository subcategoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
	}

	/**
	 * サブカテゴリーリストをDBから検索します。
	 * 対象：t_personal_subcategory.owner_idとログインユーザーIDが一致するデータ
	 * 
	 * @param loginUserId ログインユーザーID
	 * @return List<Subcategory> サブカテゴリーリスト
	 */
	public List<Subcategory> getSubcategoriesByOwnerId(String loginUserId) {
		return subcategoryRepository.findByOwnerId(loginUserId);
	}

	/**
	 * サブカテゴリーリストをDBから削除します。
	 * 対象：t_personal_subcategory.owner_idとログインユーザーIDが一致するデータ
	 * 
	 * @param loginUserId ログインユーザーID
	 */
	public void deleteSubcategoriesByOwnerId(String ownerId) {
		List<Subcategory> subcategories = getSubcategoriesByOwnerId(ownerId);
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
	public void saveSubcategories(List<Subcategory> subcategories, String loginUserId) {
		for (Subcategory subcategory : subcategories) {
			// ownerIdにログインユーザーIDを設定
			subcategory.setOwnerId(loginUserId);
			// updateDateにシステム日時を設定
			subcategory.setUpdateDate(LocalDateTime.now());
		}
		subcategoryRepository.saveAll(subcategories);
	}
}

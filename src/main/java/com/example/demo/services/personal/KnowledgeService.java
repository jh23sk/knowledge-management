package com.example.demo.services.personal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.personal.Knowledge;
import com.example.demo.models.personal.SearchCondition;
import com.example.demo.repositories.personal.KnowledgeRepository;

@Service
public class KnowledgeService {

	@PersistenceContext
	private EntityManager entityManager;

	private final KnowledgeRepository knowledgeRepository;

	public KnowledgeService(KnowledgeRepository knowledgeRepository) {
		this.knowledgeRepository = knowledgeRepository;
	}

	/**
	 * ナレッジリリストをDBから検索します。
	 * 対象：t_personal_knowledge.owner_idとログインユーザーIDが一致する、かつ画面で設定した検索条件に一致するデータ
	 * 
	 * @param searchCond 画面で設定した検索条件
	 * @param loginUserId ログインユーザーID
	 * @return List<Knowledge> 検索条件に合致するナレッジリスト
	 */
	public List<Knowledge> searchKnowledges(SearchCondition searchCond, String loginUserId) {
		String categoryId = StringUtils.trimToNull(searchCond.getCategoryId());
		String subcategoryId = StringUtils.trimToNull(searchCond.getSubcategoryId());
		String title = StringUtils.trimToNull(searchCond.getTitle());
		String content = StringUtils.trimToNull(searchCond.getContent());

		Map<String, Object> parameters = new HashMap<>();
		StringBuilder queryBuilder = new StringBuilder("SELECT k FROM Knowledge k");

		queryBuilder.append(" WHERE k.ownerId = :ownerId");
		parameters.put("ownerId", loginUserId);

		if (Objects.nonNull(categoryId)) {
			queryBuilder.append(" AND k.categoryId = :categoryId");
			parameters.put("categoryId", categoryId);
		}

		if (Objects.nonNull(subcategoryId)) {
			queryBuilder.append(" AND k.subcategoryId = :subcategoryId");
			parameters.put("subcategoryId", subcategoryId);
		}

		if (Objects.nonNull(title)) {
			queryBuilder.append(" AND k.title LIKE :title");
			parameters.put("title", "%" + title + "%");
		}

		if (Objects.nonNull(content)) {
			queryBuilder.append(" AND k.content LIKE :content");
			parameters.put("content", "%" + content + "%");
		}

		TypedQuery<Knowledge> query = entityManager.createQuery(queryBuilder.toString(), Knowledge.class);
		// パラメータ設定
		for (Map.Entry<String, Object> param : parameters.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		return query.getResultList();
	}

	/**
	 * ナレッジリリストをDBから削除します。
	 * 対象：t_personal_knowledge.owner_idとログインユーザーIDが一致する、かつ画面で設定した検索条件に一致するデータ
	 * 
	 * @param searchCond 画面で設定した検索条件
	 * @param loginUserId ログインユーザーID
	 */
	@Transactional
	public void deleteKnowledgesBySearchCond(SearchCondition searchCond, String loginUserId) {
		List<Knowledge> knowledgesToDelete = searchKnowledges(searchCond, loginUserId);
		if (!knowledgesToDelete.isEmpty()) {
			knowledgeRepository.deleteAll(knowledgesToDelete);
		}
	}

	/**
	 * ナレッジリストをDBに保存します。
	 * t_personal_knowledge.owner_idにログインユーザーID、
	 * t_personal_knowledge.update_dateにシステム日時を設定します。
	 * 
	 * @param knowledges 保存するナレッジリスト
	 * @param loginUserId ログインユーザーID
	 * @throws ConstraintViolationException 制約違反の場合
	 */
	@Transactional
	public void saveKnowledges(List<Knowledge> knowledges, String loginUserId) {
		for (Knowledge knowledge : knowledges) {
			// ownerIdにログインユーザーIDを設定
			knowledge.setOwnerId(loginUserId);
			// updateDateにシステム日時を設定
			knowledge.setUpdateDate(LocalDateTime.now());
		}
		knowledgeRepository.saveAll(knowledges);
	}
}

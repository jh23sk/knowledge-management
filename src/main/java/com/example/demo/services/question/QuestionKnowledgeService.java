package com.example.demo.services.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.demo.models.common.LoginUser;
import com.example.demo.models.question.QuestionKnowledge;
import com.example.demo.models.question.SearchCondition;
import com.example.demo.repositories.common.LoginUserRepository;
import com.example.demo.repositories.question.QuestionKnowledgeRepository;

@Service
public class QuestionKnowledgeService {

	@PersistenceContext
	private EntityManager entityManager;

	private final QuestionKnowledgeRepository knowledgeRepository;
	private final LoginUserRepository loginUserRepository;

	public QuestionKnowledgeService(QuestionKnowledgeRepository knowledgeRepository,
			LoginUserRepository loginUserRepository) {
		this.knowledgeRepository = knowledgeRepository;
		this.loginUserRepository = loginUserRepository;
	}

	/**
	 * ナレッジリリストをDBから検索します。
	 * 対象：画面で設定した検索条件に一致するデータ
	 * 
	 * @param searchCond 画面で設定した検索条件
	 * @return List<QuestionKnowledge> 検索条件に合致するナレッジリスト
	 */
	public List<QuestionKnowledge> searchKnowledges(SearchCondition searchCond) {
		String categoryId = StringUtils.trimToNull(searchCond.getCategoryId());
		String subcategoryId = StringUtils.trimToNull(searchCond.getSubcategoryId());
		String question = StringUtils.trimToNull(searchCond.getQuestion());
		String questionDateStt = StringUtils.trimToNull(searchCond.getQuestionDateStt());
		String questionDateEnd = StringUtils.trimToNull(searchCond.getQuestionDateEnd());
		String questionUserName = StringUtils.trimToNull(searchCond.getQuestionUserName());
		String answer = StringUtils.trimToNull(searchCond.getAnswer());
		String answerDateStt = StringUtils.trimToNull(searchCond.getAnswerDateStt());
		String answerDateEnd = StringUtils.trimToNull(searchCond.getAnswerDateEnd());
		String answerUserName = StringUtils.trimToNull(searchCond.getAnswerUserName());
		Boolean isAnswerd = searchCond.getIsAnswerd();

		Map<String, Object> parameters = new HashMap<>();
		StringBuilder queryBuilder = new StringBuilder("SELECT k FROM QuestionKnowledge k");
		queryBuilder.append(" LEFT JOIN LoginUser quser ON quser.id = k.questionUserId");
		queryBuilder.append(" LEFT JOIN LoginUser auser ON auser.id = k.answerUserId");
		queryBuilder.append(" WHERE 1 = 1");

		if (Objects.nonNull(categoryId)) {
			queryBuilder.append(" AND k.categoryId = :categoryId");
			parameters.put("categoryId", categoryId);
		}

		if (Objects.nonNull(subcategoryId)) {
			queryBuilder.append(" AND k.subcategoryId = :subcategoryId");
			parameters.put("subcategoryId", subcategoryId);
		}

		if (Objects.nonNull(question)) {
			queryBuilder.append(" AND k.question LIKE :question");
			parameters.put("question", "%" + question + "%");
		}

		if (Objects.nonNull(questionDateStt)) {
			LocalDateTime formatDate = LocalDateTime.parse(questionDateStt,
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			queryBuilder.append(" AND :questionDateStt <= k.questionDate");
			parameters.put("questionDateStt", formatDate);
		}

		if (Objects.nonNull(questionDateEnd)) {
			LocalDateTime formatDate = LocalDateTime.parse(questionDateEnd,
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			queryBuilder.append(" AND k.questionDate <= :questionDateEnd");
			parameters.put("questionDateEnd", formatDate);
		}

		if (Objects.nonNull(questionUserName)) {
			queryBuilder.append(" AND CONCAT(quser.nameSei, quser.nameMei) LIKE :questionUserName");
			parameters.put("questionUserName", "%" + questionUserName + "%");
		}

		if (Objects.nonNull(answer)) {
			queryBuilder.append(" AND k.answer LIKE :answer");
			parameters.put("answer", "%" + answer + "%");
		}

		if (Objects.nonNull(answerDateStt)) {
			LocalDateTime formatDate = LocalDateTime.parse(answerDateStt,
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			queryBuilder.append(" AND :answerDateStt <= k.answerDate");
			parameters.put("answerDateStt", formatDate);
		}

		if (Objects.nonNull(answerDateEnd)) {
			LocalDateTime formatDate = LocalDateTime.parse(questionDateEnd,
					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			queryBuilder.append(" AND k.answerDate <= :answerDateEnd");
			parameters.put("answerDateEnd", formatDate);
		}

		if (Objects.nonNull(answerUserName)) {
			queryBuilder.append(" AND CONCAT(auser.nameSei, auser.nameMei) LIKE :answerUserName");
			parameters.put("answerUserName", "%" + answerUserName + "%");
		}

		if (Objects.nonNull(isAnswerd)) {
			if (isAnswerd) {
				// 回答済に限定
				queryBuilder.append(" AND k.answer IS NOT NULL");
			} else {
				// 未回答に限定
				queryBuilder.append(" AND k.answer IS NULL");
			}
		}

		TypedQuery<QuestionKnowledge> query = entityManager.createQuery(queryBuilder.toString(),
				QuestionKnowledge.class);
		// パラメータ設定
		for (Map.Entry<String, Object> param : parameters.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		return query.getResultList();
	}

	/**
	 * ナレッジをDBに保存します。
	 * t_personal_knowledge.owner_idにログインユーザーID、
	 * t_personal_knowledge.update_dateにシステム日時を設定します。
	 * 
	 * @param knowledge 保存するナレッジ
	 * @param loginUserId ログインユーザーID
	 * @throws ConstraintViolationException 制約違反の場合
	 */
	@Transactional
	public void saveKnowledges(QuestionKnowledge knowledge, String loginUserId) {
		// questionUserIdにログインユーザーIDを設定
		knowledge.setQuestionUserId(loginUserId);
		// questionUserNameにログインユーザー名を設定（マスタ参照）
        LoginUser loginUser = loginUserRepository.findById(loginUserId)
            .orElseThrow(() -> new RuntimeException("LoginUser not found")); // ユーザーが見つからない場合のエラーハンドリング
        String questionUserName = loginUser.getNameSei() + " " + loginUser.getNameMei();
		knowledge.setQuestionUserName(questionUserName);
		// questionDateにシステム日時を設定
		knowledge.setQuestionDate(LocalDateTime.now());
		// updateDateにシステム日時を設定
		knowledge.setUpdateDate(LocalDateTime.now());

		knowledgeRepository.save(knowledge);
	}

	@Transactional
	public QuestionKnowledge updateQuestionKnowledge(QuestionKnowledge knowledge, String loginUserId) {
		String id = StringUtils.trimToNull(knowledge.getId());
		String answer = StringUtils.trimToNull(knowledge.getAnswer());

		// IDに紐づくナレッジを検索
		QuestionKnowledge existingKnowledge = knowledgeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("QuestionKnowledge not found"));

		// answerに入力された回答を設定
		existingKnowledge.setAnswer(answer);
		// answerUserIdにログインユーザーIDを設定
		existingKnowledge.setAnswerUserId(loginUserId);
		// answerUserNameにログインユーザー名を設定（マスタ参照）
        LoginUser loginUser = loginUserRepository.findById(loginUserId)
            .orElseThrow(() -> new RuntimeException("LoginUser not found")); // ユーザーが見つからない場合のエラーハンドリング
        String answerUserName = loginUser.getNameSei() + " " + loginUser.getNameMei();
        existingKnowledge.setAnswerUserName(answerUserName);
		// answerDateにシステム日時を設定
		existingKnowledge.setAnswerDate(LocalDateTime.now());
		// updateDateにシステム日時を設定
		existingKnowledge.setUpdateDate(LocalDateTime.now());

		// 更新を保存
		return knowledgeRepository.save(existingKnowledge);
	}
}

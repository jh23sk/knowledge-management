<template>
	<v-expansion-panels v-model="activePanel" class="mx-auto px-5 py-3" style="max-width: 1050px;">
		<v-expansion-panel>
			<v-expansion-panel-title style="background-color: #e0f7fa;">
				検索条件
			</v-expansion-panel-title>
			
			<v-expansion-panel-text>
				<!-- カテゴリー情報 -->
				<v-container class="px-1 py-2">
					<v-row>
						<v-col cols="6" class="text-left">
							<label class="form-label">カテゴリー</label>
							<SelectCategory
								v-model="searchCond.categoryId"
								:categories="categories"
								:addAble="false"
								:models="searchCond"
								@change="searchCond.subcategoryId = '';"
							/>
						</v-col>
						<v-col cols="6" class="text-left">
							<label class="form-label">サブカテゴリー</label>
							<SelectSubcategory 
								v-model="searchCond.subcategoryId" 
								:subcategories="subcategories"
								:addAble="false"
								:models="searchCond"
							/> 
						</v-col>
					</v-row>
				</v-container>
			
				<!-- 【Q&A画面用】質問情報 -->
				<v-container v-if="endpoint == 'question'" class="px-1 py-2">
					<v-row>
						<v-col cols="4" class="text-left">
							<label class="form-label">質問</label>
							<input v-model="searchCond.question" class="form-control">
						</v-col>
						<v-col cols="6" class="text-left">
							<label class="form-label">質問日時</label>
							<div class="d-flex align-items-center">
								<InputDatetime v-model="searchCond.questionDateStt" width="45%" />
								<div class="mx-1">〜</div>
								<InputDatetime v-model="searchCond.questionDateEnd" width="45%" />
							</div>
						</v-col>
						<v-col cols="2" class="text-left">
							<label class="form-label">質問者</label>
							<input v-model="searchCond.questionUserName" class="form-control">
						</v-col>
					</v-row>
				</v-container>
				
				<!-- 【Q&A画面用】回答情報 -->
				<v-container v-if="endpoint == 'question'" class="px-1 py-2">
					<v-row>
						<v-col cols="4" class="text-left">
							<label>回答</label>
							<input v-model="searchCond.answer" class="form-control">
						</v-col>
						<v-col cols="6" class="text-left">
							<label>回答日時</label>
							<div class="d-flex align-items-center">
								<InputDatetime v-model="searchCond.answerDateStt" width="45%" />
								<div class="mx-1">〜</div>
								<InputDatetime v-model="searchCond.answerDateEnd" width="45%" />
							</div>
						</v-col>
						<v-col cols="2" class="text-left">
							<label>回答者</label>
							<input v-model="searchCond.answerUserName" class="form-control">
						</v-col>
					</v-row>
				</v-container>
				
				<!-- 【個人ナレッジ画面用】タイトル・内容 -->
				<v-container v-if="endpoint == 'personal'" class="px-1 py-2">
					<v-row>
						<v-col cols="6" class="text-left">
							<label>タイトル</label>
							<input v-model="searchCond.title" class="form-control">
						</v-col>
						<v-col cols="6" class="text-left">
							<label>内容</label>
							<input v-model="searchCond.content" class="form-control">
						</v-col>
					</v-row>
				</v-container>
				
				<!-- 検索ボタン -->
				<v-container class="px-1 pt-2 pb-3">
					<v-row>
						<v-col class="text-right">
							<v-btn text="検索" @click="search()" variant="tonal" color="primary"></v-btn>
						</v-col>
					</v-row>
				</v-container>
			</v-expansion-panel-text>
		</v-expansion-panel>
	</v-expansion-panels>
</template>

<script>
//import * as Utils from '../assets/function/utils.js';
import SelectCategory from './SelectCategory.vue';
import SelectSubcategory from './SelectSubcategory.vue';
import InputDatetime from './InputDatetime.vue';

export default {
	name: 'cmp-search-cond',
	components: {
		SelectCategory,
		SelectSubcategory,
		InputDatetime,
	},
	props: {
		categories: [],
		subcategories: [],
		endpoint: String,
	},
	data() {
		return {
			activePanel: [0],
			searchCond: { categoryId: "", subcategoryId: "" },
		}
	},
	methods: {
		search() {
			console.log(this.searchCond);
			// 検索条件を渡して親の検索処理を呼ぶ
			this.$emit("call-parent-search", this.searchCond);
		},
    },
	watch: {
	},
}
</script>

<!--<style>-->
 <style scoped> 
.form-label {
	margin: 0;
}
</style>

<template>
	<v-expansion-panels v-model="activePanel" class="mx-auto px-1 py-3" style="max-width: 900px;">
		<v-expansion-panel>
			<v-expansion-panel-title style="background-color: rgb(223, 231, 249);">
				検索条件
			</v-expansion-panel-title>
			
			<v-expansion-panel-text>
				<!-- カテゴリー情報 -->
				<v-container class="px-1 py-2">
					<!-- PC表示 -->
					<span v-if="windowSize.x >= 750">
						<v-row>
							<v-col cols="6" class="pb-1 text-left">
								<label class="form-label">カテゴリー</label>
								<SelectCategory
									v-model="searchCond.categoryId"
									:categories="categories"
									:addAble="false"
									:models="searchCond"
									@change="searchCond.subcategoryId = '';"
									ref="cmpSelectCategory"
								/>
							</v-col>
							<v-col cols="6" class="pb-1 text-left">
								<label class="form-label">サブカテゴリー</label>
								<SelectSubcategory 
									v-model="searchCond.subcategoryId" 
									:subcategories="subcategories"
									:addAble="false"
									:models="searchCond"
									ref="cmpSelectSubcategory"
								/> 
							</v-col>
						</v-row>
					</span>
					
					<!-- スマホ表示 -->
					<span v-else>
						<v-row>
							<v-col class="pb-1 text-left">
								<label class="form-label">カテゴリー</label>
								<SelectCategory
									v-model="searchCond.categoryId"
									:categories="categories"
									:addAble="false"
									:models="searchCond"
									@change="searchCond.subcategoryId = '';"
									ref="cmpSelectCategory"
								/>
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<label class="form-label">サブカテゴリー</label>
								<SelectSubcategory 
									v-model="searchCond.subcategoryId" 
									:subcategories="subcategories"
									:addAble="false"
									:models="searchCond"
									ref="cmpSelectSubcategory"
								/> 
							</v-col>
						</v-row>
					</span>
				</v-container>
			
				<!-- 【Q&A画面用】質問情報 -->
				<v-container v-if="endpoint == 'question'" class="px-1 py-2">
					<!-- PC表示 -->
					<span v-if="windowSize.x >= 750">
						<v-row>
							<v-col cols="4" class="pb-1 text-left">
								<label class="form-label">質問</label>
								<input v-model="searchCond.question" class="form-control">
							</v-col>
							<v-col cols="6" class="pb-1 text-left">
								<label class="form-label">質問日時</label>
								<div class="d-flex align-items-center">
									<InputDatetime v-model="searchCond.questionDateStt" width="45%" />
									<div class="mx-1">〜</div>
									<InputDatetime v-model="searchCond.questionDateEnd" width="45%" />
								</div>
							</v-col>
							<v-col cols="2" class="pb-1 text-left">
								<label class="form-label">質問者</label>
								<input v-model="searchCond.questionUserName" class="form-control">
							</v-col>
						</v-row>
					</span>
					
					<!-- スマホ表示 -->
					<span v-else>
						<v-row>
							<v-col class="pb-1 text-left">
								<label class="form-label">質問</label>
								<input v-model="searchCond.question" class="form-control">
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<label class="form-label">質問日時</label>
								<div class="d-flex align-items-center">
									<InputDatetime v-model="searchCond.questionDateStt" width="45%" />
									<div class="mx-1">〜</div>
								</div>
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<InputDatetime v-model="searchCond.questionDateEnd" width="45%" />
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<label class="form-label">質問者</label>
								<input v-model="searchCond.questionUserName" class="form-control">
							</v-col>
						</v-row>
					</span>
				</v-container>
				
				<!-- 【Q&A画面用】回答情報 -->
				<v-container v-if="endpoint == 'question'" class="px-1 py-2">
					<!-- PC表示 -->
					<span v-if="windowSize.x >= 750">
						<v-row>
							<v-col cols="4" class="pb-1 text-left">
								<label>回答</label>
								<input v-model="searchCond.answer" class="form-control">
							</v-col>
							<v-col cols="6" class="pb-1 text-left">
								<label>回答日時</label>
								<div class="d-flex align-items-center">
									<InputDatetime v-model="searchCond.answerDateStt" width="45%" />
									<div class="mx-1">〜</div>
									<InputDatetime v-model="searchCond.answerDateEnd" width="45%" />
								</div>
							</v-col>
							<v-col cols="2" class="pb-1 text-left">
								<label>回答者</label>
								<input v-model="searchCond.answerUserName" class="form-control">
							</v-col>
						</v-row>
					</span>
					
					<!-- スマホ表示 -->
					<span v-else>
						<v-row>
							<v-col class="pb-1 text-left">
								<label>回答</label>
								<input v-model="searchCond.answer" class="form-control">
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<label>回答日時</label>
								<div class="d-flex align-items-center">
									<InputDatetime v-model="searchCond.answerDateStt" width="45%" />
									<div class="mx-1">〜</div>
								</div>
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<InputDatetime v-model="searchCond.answerDateEnd" width="45%" />
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<label>回答者</label>
								<input v-model="searchCond.answerUserName" class="form-control">
							</v-col>
						</v-row>
					</span>
				</v-container>
				
				<!-- 【Q&A画面用】回答有無 -->
				<v-container v-if="endpoint == 'question'" class="px-1 pt-2 pb-0">
					<v-row>
						<v-col class="text-left pb-0">
							<label>回答有無</label> 
							<v-radio-group v-model="searchCond.isAnswerd" inline>
								<v-radio label="全て" value=""></v-radio>
								<v-radio label="未回答" value="false"></v-radio>
								<v-radio label="回答済" value="true"></v-radio>
							</v-radio-group> 
						</v-col>
					</v-row>
				</v-container>
				
				<!-- 【個人ナレッジ画面用】タイトル・内容 -->
				<v-container v-if="endpoint == 'personal'" class="px-1 py-2">
					<!-- PC表示 -->
					<span v-if="windowSize.x >= 750">
						<v-row>
							<v-col cols="6" class="pb-0 text-left">
								<label>タイトル</label>
								<input v-model="searchCond.title" class="form-control">
							</v-col>
							<v-col cols="6" class="text-left">
								<label>内容</label>
								<input v-model="searchCond.content" class="form-control">
							</v-col>
						</v-row>
					</span>
					
					<!-- スマホ表示 -->
					<span v-else>
						<v-row>
							<v-col class="pb-0 text-left">
								<label>タイトル</label>
								<input v-model="searchCond.title" class="form-control">
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="text-left">
								<label>内容</label>
								<input v-model="searchCond.content" class="form-control">
							</v-col>
						</v-row>
					</span>
				</v-container>
				
				<!-- ボタン -->
				<v-container class="px-1 pt-0 pb-3 mt-1">
					<v-row>
						<v-col class="text-right">
							<v-btn text="検索条件クリア" @click="condClear()" variant="tonal" color="gray" class="mr-1"></v-btn>
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
		windowSize: {},
	},
	data() {
		return {
			activePanel: [0],
			searchCond: { categoryId: "", subcategoryId: "", isAnswerd: "" },
		}
	},
	methods: {
		search() {
			console.log(this.searchCond);
			// 検索条件を渡して親の検索処理を呼ぶ
			this.$emit("call-parent-search", this.searchCond);
		},
		condClear() {
			this.searchCond = { categoryId: "", subcategoryId: "", isAnswerd: "" };
			// 子コンポーネントのIDに反映
			this.$refs.cmpSelectCategory.setCategoryId(this.searchCond.categoryId);
			this.$refs.cmpSelectSubcategory.setSubcategoryId(this.searchCond.subcategoryId);
		},
    },
	watch: {
	},
}
</script>

<!-- <style scoped> -->
<style>
.form-label {
	margin: 0;
}
</style>

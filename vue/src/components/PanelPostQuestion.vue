<template>
	<v-expansion-panels v-model="activePanel" style="max-width: 900px;">
		<v-expansion-panel>
			<v-expansion-panel-title style="background-color: rgb(223, 231, 249);">
				質問投稿
			</v-expansion-panel-title>
			
			<v-expansion-panel-text>
				<!-- カテゴリー情報 -->
				<v-container class="px-1 py-2">
					<!-- PC表示 -->
					<span v-if="windowSize.x >= 600">
						<v-row>
							<v-col cols="6" class="pb-1 text-left">
								<label class="form-label">カテゴリー</label>
								<SelectCategory
									v-model="postContent.categoryId"
									:categories="categories"
									:addAble="false"
									:models="postContent"
									@change="postContent.subcategoryId = '';"
									ref="cmpSelectCategory"
								/>
							</v-col>
							<v-col cols="6" class="pb-1 text-left">
								<label class="form-label">サブカテゴリー</label>
								<SelectSubcategory 
									v-model="postContent.subcategoryId" 
									:subcategories="subcategories"
									:addAble="false"
									:models="postContent"
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
									v-model="postContent.categoryId"
									:categories="categories"
									:addAble="false"
									:models="postContent"
									@change="postContent.subcategoryId = '';"
									ref="cmpSelectCategory"
								/>
							</v-col>
						</v-row>
						<v-row class="mt-1">
							<v-col class="pb-1 text-left">
								<label class="form-label">サブカテゴリー</label>
								<SelectSubcategory 
									v-model="postContent.subcategoryId" 
									:subcategories="subcategories"
									:addAble="false"
									:models="postContent"
									ref="cmpSelectSubcategory"
								/> 
							</v-col>
						</v-row>
					</span>
				</v-container>
			
				<!-- 質問 -->
				<v-container class="px-1 py-2">
					<v-row>
						<v-col class="pb-1 text-left">
							<label class="form-label">質問</label>
							<textarea v-model="postContent.question" class="form-control textarea-enabled" />
						</v-col>
					</v-row>
				</v-container>
				
				<!-- ボタン -->
				<v-container class="px-1 pt-0 pb-3 mt-3">
					<v-row>
						<v-col class="text-right">
							<ButtonCategoryEdit
								:categories = "editingCategories" 
								:subcategories = "editingSubcategories"
								:saveBtnName="'投稿'"
								@call-parent-set-category="setCategory"
							/>
							<v-btn text="投稿" @click="post()" variant="tonal" color="primary"></v-btn>
						</v-col>
					</v-row>
				</v-container>
			</v-expansion-panel-text>
		</v-expansion-panel>
	</v-expansion-panels>
</template>

<script>
import ButtonCategoryEdit from './ButtonCategoryEdit.vue';
import SelectCategory from './SelectCategory.vue';
import SelectSubcategory from './SelectSubcategory.vue';

export default {
	name: 'cmp-panel-post-question',
	components: {
		ButtonCategoryEdit,
		SelectCategory,
		SelectSubcategory,
	},
	props: {
		categories: [],
		subcategories: [],
		windowSize: {},
	},
	data() {
		return {
			activePanel: [], // 0：パネル表示、ブランク：パネル非表示
			postContent: { categoryId: "", subcategoryId: "", question: "" },
			editingCategories: [],
			editingSubcategories: [],
		}
	},
	methods: {
		setCategory(categories, subcategories) {
			this.editingCategories = categories;
			this.editingSubcategories = subcategories;
		},
		post() {
			// 投稿内容を渡して親の投稿処理を呼ぶ
			this.$emit("call-parent-post", this.postContent, this.editingCategories, this.editingSubcategories);
		},
    },
	watch: {
	},
}
</script>

<!-- <style scoped> -->
<style>
</style>

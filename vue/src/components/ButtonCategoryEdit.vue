<template>
	<v-dialog max-width="500">
		<!-- ボタン部分 -->
		<template v-slot:activator="{ props: activatorProps }">
			<v-btn v-bind="activatorProps" color="rgb(247, 240, 179)" text="カテゴリー操作" variant="flat" class="mr-1" />
		</template>
		<!-- ダイアログ部分 -->
		<template v-slot:default="{ isActive }">
			<v-card title="カテゴリー操作">
				<v-card-text class="pt-3">
					<p>カテゴリーの新規追加・削除・名称変更を行います。</p>
					<div class="mb-3">
						<label class="form-label m-1">カテゴリー</label>
						<SelectCategory
							v-model="addedMst.categoryId"
							:categories="editingCategories"
							:addAble="true"
							:models="addedMst"
							@change="addedMst.subcategoryId = ''; addedMst.subcategoryName = '';
								addedMst.categoryName = getCategoryName(addedMst.categoryId);"
						/>
						<div v-if="addedMst.categoryId" class="d-flex align-items-center">
							<input v-model="addedMst.categoryName" class="form-control" placeholder="名称を入力してください" />
							<v-btn text="削除" v-if="addedMst.categoryId != 'new'" @click="deleteCategoryItem(1, addedMst.categoryId)" variant="outlined" color="purple"></v-btn>
						</div>
					</div>
					
					<div>
						<label class="form-label m-1">サブカテゴリー</label>
						<SelectSubcategory 
							v-model="addedMst.subcategoryId" 
							:subcategories="editingSubcategories"
							:addAble="true"
							:models="addedMst"
							@change="addedMst.subcategoryName = getSubcategoryName(addedMst.subcategoryId)"
						/>
						<div v-if="addedMst.subcategoryId" class="d-flex align-items-center">
							<input v-model="addedMst.subcategoryName" class="form-control" placeholder="名称を入力してください" />
							<v-btn text="削除" v-if="addedMst.subcategoryId != 'new'" @click="deleteCategoryItem(2, addedMst.subcategoryId)" variant="outlined" color="purple"></v-btn>
						</div>
					</div>
				</v-card-text>
				
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn text="キャンセル" @click="isActive.value = false" variant="outlined"></v-btn>
					<v-btn text="カテゴリーリストに反映" @click="isActive.value = updateMst();" :disabled="!addedMst.categoryId" variant="outlined" color="purple"></v-btn> 
				</v-card-actions>
			</v-card>
		</template>
	</v-dialog>
</template>

<script>
import $ from 'jquery';
import { v4 as uuidv4 } from 'uuid';
import * as Utils from '../assets/function/utils.js';
import SelectCategory from './SelectCategory.vue';
import SelectSubcategory from './SelectSubcategory.vue';

export default {
	name: 'cmp-btn-add-category',
	components: {
		SelectCategory,
		SelectSubcategory,
	},
	props: {
		categories: {},
		subcategories: {},
		saveBtnName: String,
	},
	data() {
		return {
			addedMst: { categoryId: "", subcategoryId: "" },
			editingCategories: [],
			editingSubcategories: [],
		}
	},
	methods: {
		/* カテゴリーのIDに対応する名称を返す */
		getCategoryName(categoryId) {
			return Utils.getMasterName(this.editingCategories, categoryId);
		},
		/* サブカテゴリーのIDに対応する名称を返す */
		getSubcategoryName(subcategoryId) {
			return Utils.getMasterName(this.editingSubcategories, subcategoryId);
		},
		/* カテゴリーリスト更新 【戻り値】エラーなし：false、エラーあり：true */
		updateMst() {
			// カテゴリーが新規追加されたか
			const IsAddedNewCategory = (this.addedMst.categoryId == "new");
			const IsAddedNewSubcategory = (this.addedMst.subcategoryId == "new");
			// 既存カテゴリーの名称が変更されたか
			const IsChangedCategoryName = !IsAddedNewCategory && (this.getCategoryName(this.addedMst.categoryId) != this.addedMst.categoryName);
			const IsChangedSubcategoryName = !IsAddedNewSubcategory && (this.getSubcategoryName(this.addedMst.subcategoryId) != this.addedMst.subcategoryName);
			
			/* エラーチェック */
			// カテゴリー・サブカテゴリーともに変更なしの場合エラー
			if((!IsAddedNewCategory && !IsChangedCategoryName) && (!IsAddedNewSubcategory && !IsChangedSubcategoryName)) {
				alert("このカテゴリーは追加済です。");
				return true;
			}
			// 未選択以外のカテゴリーを選択しているのに、名称が未入力の場合エラー
			if((this.addedMst.categoryId && !this.addedMst.categoryName)
				|| (this.addedMst.subcategoryId && !this.addedMst.subcategoryName)) {
				alert("名称を入力してください。");
				return true;
			}
			
			/* カテゴリーリストに反映 */
			// カテゴリーを追加した場合はリストに反映
			if(IsAddedNewCategory) {
				this.addedMst.categoryId = uuidv4();
				this.editingCategories.unshift(
					{ id: this.addedMst.categoryId, name: this.addedMst.categoryName },
				);
			}
			// サブカテゴリーを追加した場合はリストに反映
			if(IsAddedNewSubcategory) {
				this.addedMst.subcategoryId = uuidv4();
				this.editingSubcategories.unshift(
					{ id: this.addedMst.subcategoryId, name: this.addedMst.subcategoryName, categoryId: this.addedMst.categoryId }	
				);
			}
			// 既存のカテゴリーの名称を変更した場合はリストに反映
			if(IsChangedCategoryName) {
				const targetId = this.addedMst.categoryId;
				const newName = this.addedMst.categoryName;
				$.each(this.editingCategories,
					function(index, val) {
						if(val.id == targetId) {
							val.name = newName;
							return;
						}
					}
				);
			}
			// 既存のサブカテゴリーの名称を変更した場合はリストに反映
			if(IsChangedSubcategoryName) {
				const targetId = this.addedMst.subcategoryId;
				const newName = this.addedMst.subcategoryName;
				$.each(this.editingSubcategories,
					function(index, val) {
						if(val.id == targetId) {
							val.name = newName;
							return;
						}
					}
				);
			}
			
			// 親コンポーネントのリストに反映 
			this.$emit("call-parent-set-category", this.editingCategories, this.editingSubcategories);
			
			alert("カテゴリーリストに反映しました。" + this.saveBtnName + "ボタンクリック時に保存されます。");
			this.addedMst = [];
			return false;
		},
	},
	created() {
		this.editingCategories = this.categories;
		this.editingSubcategories = this.subcategories;
	},
}
</script>

<style>
</style>

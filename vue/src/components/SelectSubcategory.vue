<template>
	<select v-model="subcategoryId" class="form-select"> 
		<option value="" selected>末選択</option>
		<option value="new" v-if="addAble">新規追加</option>  
		<option v-for="subcategory in createSubCategories()"
			:key="subcategory.id" :value="subcategory.id"
			:selected="subcategory.id == subcategoryId"
		>
			{{ getSubcategoryName(subcategory.id) }}
		</option>
	</select> 
</template>

<script>
import * as Utils from '../assets/function/utils.js';

export default {
	name: 'cmp-select-category',
	props: {
		subcategories: [],
		models: [
			{
				catgoryId: String,
				subcategoryId: String,
			}
		],
		addAble: Boolean,
	},
	data() {
		return {
			subcategoryId: "",
		}
	},
	methods: {
		/* 中カテゴリのIDに対応する名称を返す */
		getSubcategoryName(subcategoryId) {
			const list = this.createSubCategories();
			return Utils.getMasterName(list, subcategoryId);
		},
		/* 選択した大カテゴリから、中カテゴリを絞り込んで返却  */
		createSubCategories() {
			return Utils.createSubCategories(this.subcategories, this.models.categoryId);
		},
		/* 【親から呼び出し】サブカテゴリーIDを当コンポーネントに反映 */
		setSubcategoryId(subcategoryId){
			this.subcategoryId = subcategoryId;
		},
	},
	created() {
		this.subcategoryId = this.models.subcategoryId;
	},
	watch: {
		subcategoryId(newValue) {
			this.$emit('update:subcategoryId', newValue);
		},
	},
}
</script>

<style>
</style>

<template>
	<select v-model="categoryId" class="form-select">
		<option value="">未選択</option>
		<option value="new" v-if="addAble">新規追加</option> 
		<!-- :selectedは右辺をv-modelと共通にしないと反映されない -->
		<option v-for="category in categories" :key="category.id"
			:value="category.id" :selected="category.id == categoryId">
			{{ getCategoryName(category.id) }}
		</option> 
	</select>
</template>

<script>
import * as Utils from '../assets/function/utils.js';

export default {
	name: 'cmp-select-category',
	props: {
		categories: [],
		models: [
			{
				catgoryId: String,
			}
		],
		addAble: Boolean,
	},
	data() {
		return {
			categoryId: "",
		}
	},
	methods: {
		/* 大カテゴリのIDに対応する名称を返す */
		getCategoryName(categoryId) {
			var list = this.categories;
			return Utils.getMasterName(list, categoryId);
		},
	},
	created() {
		this.categoryId = this.models.categoryId;
	},
	watch: {
		categoryId(newValue) {
			this.$emit('update:categoryId', newValue);
		},
	},
}
</script>

<!-- <style scoped> -->
<style>
</style>

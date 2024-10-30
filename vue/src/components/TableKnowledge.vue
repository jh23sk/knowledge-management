<template>
	<div v-if="endpoint == 'personal'" class="mb-2 d-flex justify-content-end">
		<span>
			<span v-if="editable">
				<ButtonCategoryEdit
					:categories="editingCategories" 
					:subcategories="editingSubcategories"
					:knowledgesSaved="knowledgesSaved"
					:editingknowledges="editingknowledges"
					:endpoint="endpoint"
					:csrfToken="csrfToken"
					:saveBtnName="'保存'"
					@call-parent-set-category="setCategory"
				/>
				<v-btn color="primary" @click="addRow()" class="mr-1">
					<FontAwesomeIcon icon="fa-solid fa-circle-plus" class="mr-1"/>行追加
				</v-btn>
				<v-btn color="primary" @click="deleteRow()" class="mr-1">
					<FontAwesomeIcon icon="fa-solid fa-circle-minus" class="mr-1"/>行削除
				</v-btn>
				<v-btn color="primary" @click="save()" class="mr-1">
					<FontAwesomeIcon icon="fa-solid fa-floppy-disk" class="mr-1"/>保存
				</v-btn>
			</span>
			<v-btn-toggle v-model="toggle" color="primary" rounded="xl" mandatory>
				<v-btn value="editMode" @click="editable = true;">
					<FontAwesomeIcon icon="fa-solid fa-pen-to-square" class="mr-1"/>編集モード
				</v-btn>
				<v-btn value="viewMode" @click="editable = false;">
					<FontAwesomeIcon icon="fa-solid fa-book-open" class="mr-1"/>閲覧モード
				</v-btn>
			</v-btn-toggle>
		</span>
	</div>
	
	<v-card flat>
		<!-- https://zenn.dev/bbled/books/vuetify3_book/viewer/sec2_20_datatable -->
		<v-data-table
			:headers="headers"
			:items="editingknowledges"
			:search="search"
			:density="compact"
			:items-per-page="100"
			:items-per-page-options="pages"
			items-per-page-text="表示行数"
			no-data-text="データがありません。"
			v-model="selected"
			item-value="id"
			:show-select="editable"
		>

			<!-- tbody -->
			<!-- ID ※非表示。チェックボックスを1つずつ選択可にするために、ユニークな値でレンダリングさせる必要がある -->
			<template v-slot:[`item.id`]="{ item }">
				<span hidden>{{ item.id }}</span>
			</template>
			
			<!-- カテゴリー -->
			<template v-slot:[`item.categoryId`]="{ item }">
				<span v-if="editable">
					<SelectCategory
						v-model="item.categoryId"
						:categories="editingCategories"
						:addAble="false"
						:models="item"
						@change="item.subcategoryId = '';"
					/>
				</span>
				<span v-else>{{ getCategoryName(item.categoryId) }}</span>
			</template>
			
			<!-- サブカテゴリー -->
			<template v-slot:[`item.subcategoryId`]="{ item }">
				<span v-if="editable">
					<SelectSubcategory 
						v-model="item.subcategoryId" 
						:subcategories="editingSubcategories"
						:addAble="false"
						:models="item"
					/>
				</span>
				<span v-else>{{ getSubcategoryName(item.subcategoryId) }}</span>
			</template>
			
			<!-- 【Q&A画面用】質問 -->
			<template v-slot:[`item.question`]="{ item }">
				<textarea v-model="item.question" class="form-control textarea-disabled p-0" readonly/>
			</template>
			
			<!-- 【Q&A画面用】回答 -->
			<template v-slot:[`item.answer`]="{ item }">
				<textarea v-if="item.answerDate" v-model="item.answer" class="form-control textarea-disabled p-0" readonly/>
				
				<v-dialog v-else max-width="700">
					<!-- ボタン部分 -->
					<template v-slot:activator="{ props: activatorProps }">
						<v-btn text="回答する" v-bind="activatorProps" color="rgb(239, 247, 201)" block />
					</template>
					<!-- ダイアログ部分 -->
					<template v-slot:default="{ isActive }">
						<v-card title="回答する">
							<v-card-text class="pt-3">
								<label class="form-label">カテゴリー「{{ getCategoryName(item.categoryId) }}」</label>
								<label class="form-label">サブカテゴリー「{{ getSubcategoryName(item.subcategoryId) }}」に関する質問</label>
								<textarea v-model="item.question" class="form-control mt-1 mb-3" style="border-width:1px; resize:none;" readonly />
								
								<label class="form-label">回答</label>
								<textarea v-model="item.answer" class="form-control textarea-enabled" />
							</v-card-text>
							<v-card-actions>
								<v-spacer></v-spacer>
								<v-btn text="キャンセル" @click="isActive.value = false" class="cancel" variant="outlined"></v-btn>
								<v-btn text="回答" @click="answer(item)" variant="outlined" color="purple"></v-btn> 
							</v-card-actions>
						</v-card>
					</template>
				</v-dialog>
			</template>
			
			<!-- 【Q&A画面用】質問日時 -->
			<template v-slot:[`item.questionDate`]="{ item }">
				<span>{{ formatDate(item.questionDate) }}</span>
			</template>
			
			<!-- 【Q&A画面用】質問者 -->
			<template v-slot:[`item.questionUserName`]="{ item }">
				<span>{{ item.questionUserName }}</span>
			</template>
			
			<!-- 【Q&A画面用】回答日時 -->
			<template v-slot:[`item.answerDate`]="{ item }">
				<span>{{ formatDate(item.answerDate) }}</span>
			</template>
			
			<!-- 【Q&A画面用】回答者 -->
			<template v-slot:[`item.answerUserName`]="{ item }">
				<span>{{ item.answerUserName }}</span>
			</template>
			
			<!-- 【個人ナレッジ画面用】タイトル -->
			<template v-slot:[`item.title`]="{ item }">
				<textarea v-if="editable" v-model="item.title" class="form-control textarea-enabled" />
				<textarea v-else v-model="item.title" class="form-control textarea-disabled p-0" readonly/>
			</template>
			
			<!-- 【個人ナレッジ画面用】内容 -->
			<template v-slot:[`item.content`]="{ item }">
				<textarea v-if="editable" v-model="item.content" class="form-control textarea-enabled" />
				<textarea v-else v-model="item.content" class="form-control textarea-disabled p-0" readonly/>
			</template>
		</v-data-table>
	</v-card>
</template>

<script>
import $ from 'jquery';
import { v4 as uuidv4 } from 'uuid';
import * as Utils from '../assets/function/utils.js';
import ButtonCategoryEdit from './ButtonCategoryEdit.vue';
import SelectCategory from './SelectCategory.vue';
import SelectSubcategory from './SelectSubcategory.vue';

export default {
	name: 'cmp-footer',
	components: {
		ButtonCategoryEdit,
		SelectCategory,
		SelectSubcategory,
	},
	props: {
		headers: [
			{
				key: String,
				title: String,
				align: String,
				sortable: String,
				width: String,
			}
		],
		endpoint: String,
		csrfToken: String,
	},
	data() {
		return {
			search: "",
			toggle: "viewMode",
			selected: [], // 削除チェックを入れた行
			editingCategories: [],
			editingSubcategories: [],
			editingknowledges: [],
			knowledgesSaved: [], // DB保存済のナレッジ
			addedMst: { categoryId: "", subcategoryId: "", categoryName: "", subcategoryName: "" },
			editable: false,
			updateFlg: false,
		};
	},
	methods: {
		/* 【親及び子から呼び出し】検索したカテゴリーリストを当コンポーネントに反映 */
		setCategory(categories, subcategories) {
			this.editingCategories = categories;
			this.editingSubcategories = subcategories;
		},
		/* 【親から呼び出し】検索したナレッジリストを当コンポーネントに反映 */
		setKnowledge(knowledges) {
			this.editingknowledges = knowledges;
			this.knowledgesSaved = knowledges;
		},
		/* 「yyyy-MM-dd HH:mm」形式にフォーマット */
		formatDate(strDate) {
			return Utils.formatDate(strDate);
		},
		/* 選択したカテゴリーから、サブカテゴリーを絞り込んで返却  */
		createSubCategories(categoryId) {
			return Utils.createSubCategories(this.editingSubcategories, categoryId);
		},
		/* カテゴリーのIDに対応する名称を返す */
		getCategoryName(categoryId) {
			return Utils.getMasterName(this.editingCategories, categoryId);
		},
		/* サブカテゴリーのIDに対応する名称を返す */
		getSubcategoryName(subcategoryId) {
			return Utils.getMasterName(this.editingSubcategories, subcategoryId);
		},
		/* 「行追加」押下時処理 */
		addRow() {
			this.editingknowledges.unshift(
				{ id: uuidv4(), categoryId: "", subcategoryId: "", title: "", content: "" },
			);
		},
		/* ナレッジの配列から、選択した行を除外して返却 */
		removeSelectedKnowledges() {
			this.selected.forEach(checkedRow => {
				this.editingknowledges = this.editingknowledges.filter(knowledge => {
					return knowledge.id != checkedRow;
				});
			});
			this.selected = [];
			return this.editingknowledges;
		},
		/* 「行削除」押下時処理 */
		deleteRow() {
			if(this.selected.length == 0) {
				alert("削除したい行を選択してください。");
			} else {
				if(confirm("選択した行を削除してよろしいですか？")) {
					this.editingknowledges = this.removeSelectedKnowledges();
					alert("選択した行を削除しました。保存ボタンで保存してください。");
				}
			}
		},
		/* 【個人ナレッジ画面用】「保存」押下時処理 */
		async save() {
			this.$emit("call-parent-save", 
				this.editingCategories, 
				this.editingSubcategories, 
				this.editingknowledges,
				(this.selected.length > 0)
			);
		},
		/* 【Q&A画面用】「回答」押下時処理 */
		async answer(item) {
			if(item.answer.length == 0) {
				alert("回答を入力してください。");
				return true;
			}
			this.$emit("call-parent-answer", item);
			// 回答ダイアログを閉じる
			$(".cancel").trigger("click");
		},
	},
	watch: {
		// 編集したらロード、検索ボタン押下前とかに「保存しますか？」ダイアログを出したい
		// 初期表示でカテゴリーはupdate検知してしまう（親から当コンポーネントの関数呼んで反映させてるため）検索したときは検知する
		// ナレッジは入力したり行追加しても検知されない
		/* editingCategories() {
			console.log("カテゴリー変更");
			this.updateFlg = true;
		},
		editingSubcategories() {
			console.log("サブカテゴリー変更");
			this.updateFlg = true;
		},
		editingknowledges() {
			console.log("ナレッジ変更");
			this.updateFlg = true;
		}, */
	},
	created() {
		console.log("knowledgetableのcreated")
	},
	mounted () {
		console.log("mountedが呼ばれました");
	},
	computed: {
		
	},
}
</script>

<!-- <style scoped> -->
<style>
</style>
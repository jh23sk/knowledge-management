<template>
	<v-container>
		<div class="mb-2 d-flex justify-content-between">
			<span class="d-flex align-items-center">
				<v-btn color="White" v-if="visibleCategories" @click="visibleCategories = false; setHeaders();" class="mr-1">
					<FontAwesomeIcon icon="fa-solid fa-eye-slash" class="mr-1"/>カテゴリ非表示
				</v-btn>
				<v-btn color="White" v-else @click="visibleCategories = true; setHeaders();" class="mr-1">
					<FontAwesomeIcon icon="fa-solid fa-eye" class="mr-1"/>カテゴリ表示
				</v-btn>
				<v-btn color="White" @click="filtersInit(); filteredKnowledges = filterKnowledges();">
					検索条件クリア
				</v-btn>
			</span>
			<span>
				<span v-if="editable">
					<!-- カテゴリ追加ボタン＆ダイアログ -->
					<v-dialog max-width="500">
						<template v-slot:activator="{ props: activatorProps }">
							<v-btn
								v-bind="activatorProps"
								color="surface-variant"
								text="カテゴリ操作"
								variant="flat"
								class="mr-1"
							></v-btn>
						</template>
						
						<template v-slot:default="{ isActive }">
							<v-card title="カテゴリ操作">
								<v-card-text>
									<p>カテゴリの新規追加・削除・名称変更を行えます。</p>
									<div class="mb-3">
										<label>大カテゴリ</label>
										<select v-model="addedMst.categoryId" class="form-select mb-1" 
										@change="addedMst.categoryName = getCategoryName(addedMst.categoryId);
												addedMst.subcategoryId = ''; addedMst.subcategoryName = '';">
											<option value="new">新規追加</option> 
											<option v-for="mstCategory in mstCategories" :key="mstCategory.id" :value="mstCategory.id"> 
												{{ getCategoryName(mstCategory.id) }} 
											</option>
										</select>
										<div v-if="addedMst.categoryId" class="d-flex align-items-center">
											<input v-model="addedMst.categoryName" class="form-control" placeholder="名称を入力してください" />
											<v-btn text="削除" v-if="addedMst.categoryId != 'new'" @click="deleteCategory()" variant="outlined" color="purple"></v-btn>
										</div>
									</div>
									
									<div>
										<label>中カテゴリ</label>
										<!-- <select v-model="addedMst.subcategoryId" class="form-select mb-1" :disabled="!addedMst.categoryId"
										@change="addedMst.subcategoryName = getSubcategoryName(addedMst.subcategoryId)"> -->
										<select v-model="addedMst.subcategoryId" class="form-select mb-1" 
										@change="addedMst.subcategoryName = getSubcategoryName(addedMst.subcategoryId)">
											<option value="" selected>末選択</option>
											<option value="new">新規追加</option> 
											<option v-for="mstSubcategory in createSubCategories(addedMst.categoryId)" :key="mstSubcategory.id" :value="mstSubcategory.id">
												{{ getSubcategoryName(mstSubcategory.id) }}
											</option>
										</select>
										<div v-if="addedMst.subcategoryId" class="d-flex align-items-center">
											<input v-model="addedMst.subcategoryName" class="form-control" placeholder="名称を入力してください" />
											<v-btn text="削除" v-if="addedMst.subcategoryId != 'new'" @click="deleteSubcategory()" variant="outlined" color="purple"></v-btn>
										</div>
									</div>
								</v-card-text>
								
								<v-card-actions>
									<v-spacer></v-spacer>
									<v-btn text="キャンセル" @click="isActive.value = false" variant="outlined"></v-btn>
									<v-btn text="カテゴリリストに反映" @click="isActive.value = updateMst();" :disabled="!addedMst.categoryId" variant="outlined" color="purple"></v-btn> 
								</v-card-actions>
							</v-card>
						</template>
					</v-dialog>
				
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
					<v-btn value="editMode" @click="editable = true; showCategoryColumns();">
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
				:items="filteredKnowledges"
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
			
				<!-- thead -->
				<!-- 大カテゴリ -->
				<template v-slot:[`header.categoryId`]="{ column }">
					<span>{{ column.title }}</span>
					<select v-model="filters.categoryId" class="form-select" @change="filters.subcategoryId = ''">
						<option value="" selcted>末選択</option>
						<option v-for="mstCategory in mstCategories" :key="mstCategory.id" :value="mstCategory.id">
							{{ getCategoryName(mstCategory.id) }}
						</option>
					</select>
				</template>
				
				<!-- 中カテゴリ -->
				<template v-slot:[`header.subcategoryId`]="{ column }">
					<span>{{ column.title }}</span>
					<!-- <select v-model="filters.subcategoryId" :disabled="!filters.categoryId" class="form-select"> -->
					<select v-model="filters.subcategoryId" class="form-select">
						<option value="" selcted>末選択</option>
						<option v-for="mstSubcategory in createSubCategories(filters.categoryId)" :key="mstSubcategory.id" :value="mstSubcategory.id">
							{{ getSubcategoryName(mstSubcategory.id) }}
						</option>
					</select>
				</template>
				
				<!-- タイトル -->
				<template v-slot:[`header.title`]="{ column }">
					<span>{{ column.title }}</span>
					<input v-model="filters.title" class="form-control" placeholder="検索条件を入力してください">
				</template>
				
				<!-- 内容 -->
				<template v-slot:[`header.content`]="{ column }">
					<span>{{ column.title }}</span>
					<input v-model="filters.content" class="form-control" placeholder="検索条件を入力してください">
				</template>

				<!-- tbody -->
				<!-- ID ※非表示。チェックボックスを1つずつ選択可にするために、ユニークな値でレンダリングさせる必要がある -->
				<template v-slot:[`item.id`]="{ item }">
					<span hidden>{{ item.id }}</span>
				</template>
				
				<!-- 大カテゴリ -->
				<template v-slot:[`item.categoryId`]="{ item }">
					<span v-if="editable">
						<select v-model="item.categoryId" class="form-select" @change="item.subcategoryId = '';">
							<option value="">未選択</option>
							<option v-for="mstCategory in mstCategories" :key="mstCategory.id" :value="mstCategory.id" :selected="mstCategory.id == item.categoryId"> 
								{{ getCategoryName(mstCategory.id) }} 
							</option>
						</select>
					</span>
					<span v-else>{{ getCategoryName(item.categoryId) }}</span>
				</template>
				
				<!-- 中カテゴリ -->
				<template v-slot:[`item.subcategoryId`]="{ item }">
					<span v-if="editable">
						<!-- <select v-model="item.subcategoryId" :disabled="!item.categoryId" class="form-select"> -->
						<select v-model="item.subcategoryId" class="form-select">
							<option value="">末選択</option>
							<option v-for="mstSubcategory in createSubCategories(item.categoryId)" :key="mstSubcategory.id" :value="mstSubcategory.id" :selected="mstSubcategory.id == item.subcategoryId">
								{{ getSubcategoryName(mstSubcategory.id) }}
							</option>
						</select>
					</span>
					<span v-else>{{ getSubcategoryName(item.subcategoryId) }}</span>
				</template>
				
				<!-- タイトル -->
				<template v-slot:[`item.title`]="{ item }">
					<textarea v-if="editable" v-model="item.title" class="form-control" style="border-width:1px; resize:vertical;"/>
					<textarea v-else v-model="item.title" class="form-control p-0" style="border-width:0px; resize:none;" readonly/>
				</template>
				
				<!-- 内容 -->
				<template v-slot:[`item.content`]="{ item }">
					<textarea v-if="editable" v-model="item.content" class="form-control" style="border-width:1px; resize:vertical;"/>
					<textarea v-else v-model="item.content" class="form-control p-0" style="border-width:0px; resize:none;" readonly/>
				</template>
			</v-data-table>
		</v-card>
	</v-container>
</template>

<script>
import $ from 'jquery';
import { v4 as uuidv4 } from 'uuid';
import axios from 'axios';

export default {
	name: 'cmp-footer',
	props: {
		propHeaders: [
			{
				key: String,
				title: String,
				align: String,
				sortable: String,
				width: String,
			}
		]
	},
	data() {
		return {
			search: "",
			toggle: "viewMode",
			selected: [], // 削除チェックを入れた行
			mstCategories: [],
			mstSubcategories: [],
			/* headers: [
				// ID列は非表示なのでデータ不要
				{ key: "categoryId", title: "大カテゴリ", align: "center", sortable: false, width: "15%" },
				{ key: "subcategoryId", title: "中カテゴリ", align: "center", sortable: false, width: "15%" },
				{ key: "title", title: "タイトル", align: "center", sortable: false, width: "25%" },
				{ key: "content", title: "内容", align: "center", sortable: false, width: "45%" },
			], */
			headers: [],
			knowledges: [],
			knowledgesSaved: [], // DB保存済のナレッジ
			filters: [],
			addedMst: [],
			editable: false,
			visibleCategories: true,
		};
	},
	methods: {
		/* 大カテゴリのIDに対応する名称を返す */
		getCategoryName(categoryId) {
			const category = this.mstCategories.find(category => category.id === categoryId);
			return category ? category.name : "";
		},
		/* 中カテゴリのIDに対応する名称を返す */
		getSubcategoryName(subcategoryId) {
			const subcategory = this.mstSubcategories.find(subcategory => subcategory.id === subcategoryId);
			return subcategory ? subcategory.name : "";
		},
		/* カテゴリリスト更新 【戻り値】エラーなし：false、エラーあり：true */
		updateMst() {
			// カテゴリが新規追加されたか
			const IsAddedNewCategory = (this.addedMst.categoryId == "new");
			const IsAddedNewSubcategory = (this.addedMst.subcategoryId == "new");
			// 既存カテゴリの名称が変更されたか
			const IsChangedCategoryName = !IsAddedNewCategory && (this.getCategoryName(this.addedMst.categoryId) != this.addedMst.categoryName);
			const IsChangedSubcategoryName = !IsAddedNewSubcategory && (this.getSubcategoryName(this.addedMst.subcategoryId) != this.addedMst.subcategoryName);
			
			/* エラーチェック */
			// 大カテゴリ・中カテゴリともに変更なしの場合エラー
			if((!IsAddedNewCategory && !IsChangedCategoryName) && (!IsAddedNewSubcategory && !IsChangedSubcategoryName)) {
				alert("このカテゴリは追加済です。");
				return true;
			}
			// 未選択以外のカテゴリを選択しているのに、名称が未入力の場合エラー
			if((this.addedMst.categoryId && !this.addedMst.categoryName)
				|| (this.addedMst.subcategoryId && !this.addedMst.subcategoryName)) {
				alert("名称を入力してください。");
				return true;
			}
			
			/* カテゴリリストに反映 */
			// 大カテゴリを追加した場合はリストに反映
			if(IsAddedNewCategory) {
				this.addedMst.categoryId = uuidv4();
				this.mstCategories.unshift(
					{ id: this.addedMst.categoryId, name: this.addedMst.categoryName },
				);
			}
			// 中カテゴリを追加した場合はリストに反映
			if(IsAddedNewSubcategory) {
				this.addedMst.subcategoryId = uuidv4();
				this.mstSubcategories.unshift(
					{ id: this.addedMst.subcategoryId, name: this.addedMst.subcategoryName, categoryId: this.addedMst.categoryId }	
				);
			}
			// 既存の大カテゴリの名称を変更した場合はリストに反映
			if(IsChangedCategoryName) {
				const targetId = this.addedMst.categoryId;
				const newName = this.addedMst.categoryName;
				$.each(this.mstCategories,
					function(index, val) {
						if(val.id == targetId) {
							val.name = newName;
							return;
						}
					}
				);
			}
			// 既存の中カテゴリの名称を変更した場合はリストに反映
			if(IsChangedSubcategoryName) {
				const targetId = this.addedMst.subcategoryId;
				const newName = this.addedMst.subcategoryName;
				$.each(this.mstSubcategories,
					function(index, val) {
						if(val.id == targetId) {
							val.name = newName;
							return;
						}
					}
				);
			}
			
			alert("カテゴリリストに反映しました。保存ボタンで保存してください。");
			this.addedMst = [];
			return false;
		},
		/* 大カテゴリ削除 【戻り値】エラーなし：false、エラーあり：true */
		deleteCategory() {
			if(confirm("この大カテゴリを削除しますか？")) {
				/* エラーチェック */
				if(this.createSubCategories(this.addedMst.categoryId).length > 0) {
					alert("配下の中カテゴリが存在するため削除できません。");
					return true;
				}
				
				// 保存済のナレッジで使用していないか
				const fil1 = this.knowledgesSaved.filter(knowledge => {
					return knowledge.categoryId == this.addedMst.categoryId;
				});
				// 保存前のナレッジで使用していないか
				const fil2 = this.knowledges.filter(knowledge => {
					return knowledge.categoryId == this.addedMst.categoryId;
				});
				if(fil1.length > 0 || fil2.length > 0) {
					alert("ナレッジに設定中のため削除できません。");
					return true;
				}
				
				/* 対象の大カテゴリを削除 */
				this.mstCategories = this.mstCategories.filter(mstCategory => {
					return mstCategory.id != this.addedMst.categoryId;
				});
				alert("大カテゴリを削除しました。保存ボタンで保存してください。");
				this.addedMst = [];
				return false;
			}
		},
		/* 中カテゴリ削除 【戻り値】エラーなし：false、エラーあり：true */
		deleteSubcategory() {
			if(confirm("この中カテゴリを削除しますか？")) {
				/* エラーチェック */
				// 保存済のナレッジで使用していないか
				const fil1 = this.knowledgesSaved.filter(knowledge => {
					return knowledge.subcategoryId == this.addedMst.subcategoryId;
				});
				// 保存前のナレッジで使用していないか
				const fil2 = this.knowledges.filter(knowledge => {
					return knowledge.subcategoryId == this.addedMst.subcategoryId;
				});
				if(fil1.length > 0 || fil2.length > 0) {
					alert("ナレッジに設定中のため削除できません。");
					return true;
				}
				
				/* 対象の中カテゴリを削除 */
				this.mstSubcategories = this.mstSubcategories.filter(mstSubcategory => {
					return mstSubcategory.id != this.addedMst.subcategoryId;
				});
				alert("中カテゴリを削除しました。保存ボタンで保存してください。");
				this.addedMst.subcategoryId = "";
				this.addedMst.subcategoryName = "";
				return false;
			}
		},
		/* 編集可の場合、カテゴリ列を表示させる */
		showCategoryColumns() {
			if(this.editable) {
				this.visibleCategories = true;
				this.setHeaders();
			}
		},
		/* カテゴリ表示状態によってヘッダを設定 */
		setHeaders() {
			if(this.visibleCategories) {
				// カテゴリ表示
				this.headers = [
					{ key: "categoryId", title: "大カテゴリ", align: "center", sortable: false, width: "15%" },
					{ key: "subcategoryId", title: "中カテゴリ", align: "center", sortable: false, width: "15%" },
					{ key: "title", title: "タイトル", align: "center", sortable: false, width: "25%" },
					{ key: "content", title: "内容", align: "center", sortable: false, width: "45%" },
				];
			} else {
				// カテゴリ非表示
				this.headers = [
					{ key: "title", title: "タイトル", align: "center", sortable: false, width: "35%" },
					{ key: "content", title: "内容", align: "center", sortable: false, width: "65%" },
				];
			}
		},
		addRow() {
			this.knowledges.unshift(
				{ id: uuidv4(), categoryId: "", subcategoryId: "", title: "", content: "" },
			);
		},
		deleteRow() {
			const checkedRows = this.selected;
			
			if(checkedRows.length == 0) {
				alert("削除したい行を選択してください。");
			} else {
				if(confirm("選択した行を削除してよろしいですか？")) {
					checkedRows.forEach(checkedRow => {
						this.knowledges = this.knowledges.filter(knowledge => {
							// 選択した行以外で配列を生成し直す
							return knowledge.id != checkedRow;
						});
					});
					this.selected = [];
					alert("選択した行を削除しました。保存ボタンで保存してください。");
				}
			}
		},
		async save(){
			if(this.selected.length > 0) {
				if(confirm("選択された行を削除し、保存してよろしいですか？")) {
					this.deleteRow();
				}
			}
			
			console.log(this.knowledges);
			console.log(this.search);
			
			// サーバー側の保存処理を呼ぶ
			/* try {
				const response = await axios.post('/personal/save', {
					//name: this.categoryName, // 送信するデータ
				});
				console.log('Data submitted:', response.data);
				// フォーム送信後の処理（リセットやメッセージ表示など）
			} catch (error) {
				console.error('Error submitting data:', error);
			} */
			
			//this.init(); //ここで初期化すると順番変わってしまうので不要
		},
		/* 選択した大カテゴリから、中カテゴリを絞り込んで返却  */
		createSubCategories(categoryId) {
			const showSubCategories = this.mstSubcategories.filter(mstSubcategory => {
				return mstSubcategory.categoryId == categoryId;
			});
			return showSubCategories;
		},
		/* サーバー側から保存済データを取得し、配列に設定 */
		init() {
			axios.get(`${window.location.origin}/personal/init`)
			.then(response => {
				this.mstCategories = response.data.categories;
				this.mstSubcategories = response.data.subcategories;
				this.knowledges = response.data.knowledges;
				this.knowledgesSaved = response.data.knowledges;
				console.log(response.data);
			})
			.catch(error => {
				console.error("初期設定エラー:", error);
			});
		},
		/* 検索条件の配列を初期化 */
		filtersInit() {
			this.filters = { categoryId: "", subcategoryId: "", title: "", content: "" };
		},
		/* 検索条件によってナレッジを絞り込む */
		filterKnowledges() {
			return this.knowledges.filter(item => {
				return (
						(!this.filters.categoryId || item.categoryId === this.filters.categoryId) &&
						(!this.filters.subcategoryId || item.subcategoryId === this.filters.subcategoryId) &&
						item.title.toLowerCase().includes(this.filters.title.toLowerCase()) &&
						item.content.toLowerCase().includes(this.filters.content.toLowerCase())
				);
			});
		},
	},
	mounted () {
		console.log("mountedが呼ばれました");
		//this.setHeaders();
		this.filtersInit();
	},
	onMounted() {
		console.log("onMountedが呼ばれました");
	},
	beforeUpdate() {
		console.log("beforeUpdateが呼ばれました");
	},
	onUpdated() {
		console.log("updatedが呼ばれました");
	},
	created() {
		this.init();
		this.headers = this.propHeaders;
	},
	computed: {
		filteredKnowledges() {
			return this.filterKnowledges();
		}
	},
}
</script>

<!-- <style scoped> -->
<style>
</style>
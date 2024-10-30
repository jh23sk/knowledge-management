<template>
	<v-layout>
		<NavBar/>
		<v-main>
			<span v-resize="onResize"></span>
			<header class="pt-5">
				<h3 class="display-6">
					<span class="markerGray">
						個人ナレッジ
					</span>
				</h3>
			</header>

			<div class="mx-4 mb-5">
				<!-- 検索条件 -->
				<PanelSearchCond 
					:categories="categories" 
					:subcategories="subcategories"
					:endpoint="endpoint"
					:csrfToken="csrfToken"
					:windowSize="windowSize"
					@call-parent-search="search"
					class="mx-auto px-1 pt-3 pb-4"
				/>

				<!-- ナレッジ一覧 -->
				<TableKnowledge
					:headers="headers"
					:endpoint="endpoint"
					ref="cmpKnowledge"
					@call-parent-save="save"
				/>
			</div>
			
			<Footer/>
		</v-main>
	</v-layout>
</template>

<script>
//import $ from 'jquery';
import axios from 'axios';
import qs from 'qs';
import NavBar from './components/NavBar.vue';
import PanelSearchCond from './components/PanelSearchCond.vue';
import TableKnowledge from './components/TableKnowledge.vue';
import Footer from './components/Footer.vue';

export default {
	name: 'App',
	components: {
		NavBar,
		PanelSearchCond,
		TableKnowledge,
		Footer,
	},
	data() {
		return {
			windowSize: {
				x: 0,
				y: 0,
			},
			csrfToken: "",
			headers: [
				// ID列は非表示なのでデータ不要
				{ key: "categoryId", title: "カテゴリー", align: "center", sortable: false, width: "15%" },
				{ key: "subcategoryId", title: "サブカテゴリー", align: "center", sortable: false, width: "15%" },
				{ key: "title", title: "タイトル", align: "center", sortable: false, width: "25%" },
				{ key: "content", title: "内容", align: "center", sortable: false, width: "45%" },
			],
			categories: [],
			subcategories: [],
			knowledges: [],
			endpoint: "personal",
			searchCond: {},
		};
	},
	methods: {
		/* CSRFトークン取得 */
		async fetchCsrfToken() {
			try {
				const response = await axios.get("/csrf-token");
				this.csrfToken = response.data.csrfToken;
			} catch (error) {
				console.error("CSRFトークンの取得に失敗しました:", error);
			}
        },
		onResize() {
			this.windowSize = { x: window.innerWidth, y: window.innerHeight }
		},
		/* カテゴリーのマスタデータを検索 */
		searchCategoryList() {
			axios.get("/" + this.endpoint + "/searchCategory")
			.then(response => {
				this.categories = response.data.categories;
				this.subcategories = response.data.subcategories;
				console.log("↓searchCategoryのレスポンス");
				console.log(response.data);
				
				// 子コンポーネントの配列に反映
				this.$refs.cmpKnowledge.setCategory(this.categories, this.subcategories);
			})
			.catch(error => {
				console.error("初期設定エラー:", error);
			});
		},
		/* 「検索」押下時処理 */
		async search(inputSearchCond) {
			try {
				const response = await axios.post("/" + this.endpoint + "/searchKnowledge", qs.stringify({
					searchCondition: JSON.stringify(inputSearchCond)
				}), {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'X-CSRF-TOKEN': this.csrfToken,
					},
					withCredentials: true
				});
				
				this.knowledges = response.data.knowledges;
				this.searchCond = inputSearchCond;
				
				// 子コンポーネントの配列に反映
				this.$refs.cmpKnowledge.setKnowledge(this.knowledges);
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("検索が失敗しました。再試行してください。");
			}
		},
		/* 「保存」押下時処理 */
		async save(categories, subcategories, knowledges, IsSelected) {
			if(IsSelected) {
				if(confirm("選択した行を削除し、保存してよろしいですか？")) {
					knowledges = this.$refs.cmpKnowledge.removeSelectedKnowledges();
				} else {
					return true;
				}
			}
            
			try {
				const searchCondition = this.searchCond;
				const response = await axios.post("/" + this.endpoint + "/save", qs.stringify({
					categories: JSON.stringify(categories),
					subcategories: JSON.stringify(subcategories),
					knowledges: JSON.stringify(knowledges),
					searchCondition: JSON.stringify(searchCondition)
				}), {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'X-CSRF-TOKEN': this.csrfToken,
					},
					withCredentials: true
				});
				
				
				console.log("Data submitted:", response.data);
				alert("保存しました。");
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("保存が失敗しました。再試行してください。");
			}
		},
	},
	beforeCreate () {
	},
	created() {
		console.log("personalのcreated");
		this.searchCategoryList();
	},
	async mounted () {
		await this.fetchCsrfToken();
		this.onResize();
	},
}
</script>

<style>
</style>

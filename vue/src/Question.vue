<template>
	<v-layout>
		<NavBar/>
		<v-main>
			<span v-resize="onResize"></span>
			<header class="pt-5">
				<h3 class="display-6">
					<span class="markerGray">
						Q&A
					</span>
				</h3>
			</header>

			<div class="mx-3 mb-5">
				<!-- 検索条件 -->
				<PanelSearchCond 
					:categories="categories" 
					:subcategories="subcategories"
					:endpoint="endpoint"
				/>
				
				<!-- ナレッジテーブル -->
				<KnowledgeTable
					:headers="headers"
					:endpoint="endpoint" 
					:searchCond="searchCond" 
				/>
			</div>
			
			<Footer/>
		</v-main>
	</v-layout>
</template>

<script>
//import $ from 'jquery';
import axios from 'axios';
import NavBar from './components/NavBar.vue';
import PanelSearchCond from './components/PanelSearchCond.vue';
import KnowledgeTable from './components/KnowledgeTable.vue';
import Footer from './components/Footer.vue';

export default {
	name: 'App',
	components: {
		NavBar,
		PanelSearchCond,
		KnowledgeTable,
		Footer,
	},
	data() {
		return {
			windowSize: {
				x: 0,
				y: 0,
			},
			headers: [
				{ key: "categoryId", title: "カテゴリー", align: "center", sortable: false, width: "10%" },
				{ key: "subcategoryId", title: "サブカテゴリー", align: "center", sortable: false, width: "10%" },
				{ key: "question", title: "質問", align: "center", sortable: false, width: "20%" },
				{ key: "answer", title: "回答", align: "center", sortable: false, width: "20%" },
				{ key: "questionDate", title: "質問日時", align: "center", sortable: true, width: "10%" },
				{ key: "questionUserName", title: "質問者", align: "center", sortable: false, width: "10%" },
				{ key: "answerDate", title: "回答日時", align: "center", sortable: true, width: "10%" },
				{ key: "answerUserName", title: "回答者", align: "center", sortable: false, width: "10%" },
			],
			categories: [],
			subcategories: [],
			endpoint: "question",
			//endpoint: "personal",
			searchCond: [],
		};
	},
	methods: {
		onResize() {
			this.windowSize = { x: window.innerWidth, y: window.innerHeight }
		},
		search() {
			console.log(this.searchCond);
		},
		/* サーバー側から保存済データを取得し、配列に設定 */
		searchCategoryList() {
			//axios.get(`${window.location.origin}` + "/" + this.endpoint + "/searchCategory")
			axios.get(`${window.location.origin}/personal/searchCategory`)
			.then(response => {
				this.categories = response.data.categories;
				this.subcategories = response.data.subcategories;
				
				// 子コンポーネントの配列に反映
				this.$refs.cmpKnowledge.setCategory(this.categories, this.subcategories);
			})
			.catch(error => {
				console.error("初期設定エラー:", error);
			});
		},
	},
	beforeCreate () {
		//this.searchAllKnowledges();
	},
	created() {
		console.log("createdが呼ばれました");
		this.searchCategoryList();
	},
	mounted () {
		this.onResize()
	},
}
</script>

<style>
</style>

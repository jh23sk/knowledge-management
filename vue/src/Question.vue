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

			<div class="mx-4 mb-5">
				<!-- 質問投稿 -->
				<PanelPostQuestion 
					:categories="categories" 
					:subcategories="subcategories"
					:windowSize="windowSize"
					ref="cmpPost"
					@call-parent-post="post"
				/>
				
				<!-- 検索条件 -->
				<PanelSearchCond 
					:categories="categories" 
					:subcategories="subcategories"
					:endpoint="endpoint"
					:windowSize="windowSize"
					@call-parent-search="search"
				/>
				
				<!-- ナレッジテーブル -->
				<TableKnowledge
					:headers="headers"
					:endpoint="endpoint" 
					:searchCond="searchCond"
					ref="cmpKnowledge"
					@call-parent-answer="answer"
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
import PanelPostQuestion from './components/PanelPostQuestion.vue';
import PanelSearchCond from './components/PanelSearchCond.vue';
import TableKnowledge from './components/TableKnowledge.vue';
import Footer from './components/Footer.vue';

export default {
	name: 'App',
	components: {
		NavBar,
		PanelPostQuestion,
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
			headers: [
				// ID列は非表示なのでデータ不要
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
			knowledges: [],
			endpoint: "question",
			searchCond: {},
		};
	},
	methods: {
		onResize() {
			this.windowSize = { x: window.innerWidth, y: window.innerHeight }
		},
		/* search() {
			console.log(this.searchCond);
		}, */
		/* カテゴリーのマスタデータを検索 */
		searchCategoryList() {
			//axios.get(`${window.location.origin}` + "/" + this.endpoint + "/searchCategory")
			axios.get(`${window.location.origin}/personal/searchCategory`)
			.then(response => {
				this.categories = response.data.categories;
				this.subcategories = response.data.subcategories;
				
				// 子コンポーネントの配列に反映
				this.$refs.cmpPost.setCategory(this.categories, this.subcategories);
				this.$refs.cmpKnowledge.setCategory(this.categories, this.subcategories);
			})
			.catch(error => {
				console.error("初期設定エラー:", error);
			});
		},
		/* 「投稿」押下時処理 */
		async post(postContent, categories, subcategories) {
			try {
				//const response = await axios.post("/" + this.endpoint + "/postQuestion", postContent);
				console.log(postContent);
				console.log(categories);
				console.log(subcategories);
				alert("質問を投稿しました。");
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("質問の投稿が失敗しました。再試行してください。");
			}
		},
		/* 「検索」押下時処理 */
		async search(inputSearchCond) {
			try {
				//const response = await axios.post("/" + this.endpoint + "/searchKnowledge", inputSearchCond);
				//this.knowledges = response.data.knowledges;
				this.searchCond = inputSearchCond;
				
				// デバッグ用！！！
				this.knowledges = [
					{
						id: "sampleId1",
						categoryId: "345f3be3-879a-417e-bd1a-3f0f6d7a3d7f",
						subcategoryId: "dc45cfa0-b288-42bc-a8c2-3b5e9fcc34ec",
						question: "【質問1】〜〜〜ですか？",
						answer: "【回答1】〜〜〜です。",
						questionDate: "2024-10-24 10:58:32.984786",
						questionUserName: "社員 太郎",
						answerDate: "2024-10-25 10:58:32.984786",
						answerUserName: "社員 花子",
						isAnswerd: true,
					},
					{
						id: "sampleId2",
						categoryId: "345f3be3-879a-417e-bd1a-3f0f6d7a3d7f",
						subcategoryId: "dc45cfa0-b288-42bc-a8c2-3b5e9fcc34ec",
						question: "【質問2】〜〜〜ですか？",
						answer: "",
						questionDate: "2024-10-26 14:58:32.984786",
						questionUserName: "社員 太郎",
						answerDate: "",
						answerUserName: "",
						isAnswerd: false,
					},
				];
				
				// 子コンポーネントの配列に反映
				this.$refs.cmpKnowledge.setKnowledge(this.knowledges);
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("検索が失敗しました。再試行してください。");
			}
		},
		/* 「回答」押下時処理 */
		async answer(item) {
			try {
/* 				const response = await axios.post("/" + this.endpoint + "/answer", { categories, subcategories, knowledges, searchCondition });
				console.log("Data submitted:", response.data); */
				console.log(item);
				alert("回答しました。");
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("回答が失敗しました。再試行してください。");
			}
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

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
					:knowledges="knowledges"
					:endpoint="endpoint"
					:csrfToken="csrfToken"
					:windowSize="windowSize"
					ref="cmpPost"
					@call-parent-post="post"
					class="mx-auto px-1 py-3"
				/>
				
				<!-- 検索条件 -->
				<PanelSearchCond 
					:categories="categories" 
					:subcategories="subcategories"
					:endpoint="endpoint"
					:windowSize="windowSize"
					@call-parent-search="search"
					class="mx-auto px-1 pt-3 pb-4"
				/>
				
				<!-- ナレッジ一覧 -->
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
import $ from 'jquery';
import axios from 'axios';
import qs from 'qs';
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
			axios.get(`${window.location.origin}` + "/" + this.endpoint + "/searchCategory")
			//axios.get(`${window.location.origin}/personal/searchCategory`)
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
				const response = await axios.post("/" + this.endpoint + "/question", qs.stringify({
					categories: JSON.stringify(categories),
					subcategories: JSON.stringify(subcategories),
					postContent: JSON.stringify(postContent),
				}), {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'X-CSRF-TOKEN': this.csrfToken,
					},
					withCredentials: true
				});
				
				$("#searchButton").trigger("click");
				
				console.log("Data submitted:", response.data);
				alert("質問を投稿しました。");
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("質問の投稿が失敗しました。再試行してください。");
			}
		},
		/* 「検索」押下時処理 */
		async search(inputSearchCond) {
			try {
				
				console.log(inputSearchCond);
				
				const response = await axios.post("/" + this.endpoint + "/searchKnowledge", qs.stringify({
					searchCondition: JSON.stringify(inputSearchCond)
				}), {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'X-CSRF-TOKEN': this.csrfToken,
					},
					withCredentials: true
				});
				
				console.log(response.data);
				
				this.knowledges = response.data.knowledges;
				this.searchCond = inputSearchCond;
				
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
				const response = await axios.post("/" + this.endpoint + "/answer", qs.stringify({
					postContent: JSON.stringify(item),
				}), {
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded',
						'X-CSRF-TOKEN': this.csrfToken,
					},
					withCredentials: true
				});
				
				$("#searchButton").trigger("click");
				
				console.log("Data submitted:", response.data);
				alert("回答しました。");
				
			} catch (error) {
				console.error("Error submitting data:", error);
				alert("回答が失敗しました。再試行してください。");
			}
		},
	},
	created() {
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

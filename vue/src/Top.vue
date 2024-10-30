<template>
	<v-layout>
		<NavBar/>
		<v-main>
			<span v-resize="onResize"></span>
			<header class="pt-5">
				<h3 class="display-6">ナレッジ管理ツール</h3>
			</header>
	
			<!-- Q&A -->
			<section class="section pt-5" id="contact">
				<h3 class="fs-2">
					<span class="markerGray">
						<FontAwesomeIcon icon="fa-solid fa-comments" class="mr-3"/>
						<a href="/question">Q&A</a>
					</span>
				</h3>
				<v-container class="pt-2">
					<v-card elevation="16" class="mx-auto bg-clear-white" max-width="450">
						<v-card-text>
							<p class="text fs-6 m-0 px-3 py-1">
								業務に関する質問の投稿/回答/検索をします。<br>
							</p>
						</v-card-text>
					</v-card>
				</v-container>
			</section>
			
			<!-- 個人ナレッジ -->
			<section class="section pt-5 pb-5" id="contact">
				<h3 class="fs-2">
					<span class="markerGray">
						<FontAwesomeIcon icon="fa-solid fa-cube" class="mr-3"/>
						<a href="/personal">個人ナレッジ</a>
					</span>
				</h3>
				<v-container class="pt-2">
					<v-card elevation="16" class="mx-auto bg-clear-white" max-width="450">
						<v-card-text>
							<p class="text fs-6 m-0 px-3 py-1">
								個人的なナレッジの登録/検索をします。<br>
								他のユーザーには公開されません。
							</p>
						</v-card-text>
					</v-card>
				</v-container>
			</section>
		
			<Footer/>
		</v-main>
	</v-layout>
</template>

<script>
//import $ from 'jquery';
import NavBar from './components/NavBar.vue';
import Footer from './components/Footer.vue';

export default {
	name: 'App',
	components: {
		NavBar,
		Footer,
	},
	data() {
		return {
			windowSize: {
				x: 0,
				y: 0,
			},
		};
	},
	mounted () {
		this.onResize()
	},
	methods: {
		onResize () {
			this.windowSize = { x: window.innerWidth, y: window.innerHeight }
		},
	},
}
</script>

<style>
</style>

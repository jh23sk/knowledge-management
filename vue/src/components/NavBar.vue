<template>
	<v-app-bar app>
		<v-app-bar-title class="text-left pc-only">
			<a href="/top">ナレッジ管理ツール</a>
		</v-app-bar-title>
		<template v-slot:append>
			<button type="button" class="btn btn-light mr-1">
				<a href="/question">
					<FontAwesomeIcon icon="fa-solid fa-comments" class="mr-1"/>Q&A
				</a>
			</button>
			<button type="button" class="btn btn-light mr-1">
				<a href="/personal">
					<FontAwesomeIcon icon="fa-solid fa-cube" class="mr-1"/>個人ナレッジ
				</a>
			</button>
			<button type="button" class="btn btn-light mr-1">
				<a href="/logout">
					<FontAwesomeIcon icon="fa-solid fa-right-from-bracket" class="mr-1"/>ログアウト
				</a>
			</button>
						
			<!-- <FontAwesomeIcon icon="fa-solid fa-address-card" class="mr-1"/><a href="#profile" class="mr-3">マイページ</a> -->
		</template>
	</v-app-bar>
</template>

<script>
export default {
	name: 'cmp-nav-bar',
	props: {
	},
}
</script>

<!-- <style scoped> -->
<style>
/* a {
	font-size: 18px;
} */
</style>

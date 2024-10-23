module.exports = {
	//outputDir: 'docs',
	assetsDir: './',
	publicPath: './',
	pages: {
		top: {
			entry: "src/mainTop.js",
			title: "ナレッジ管理ツール"
		},
		question: {
			entry: "src/mainQuestion.js",
			title: "Q&A"
		},
		personal: {
			entry: "src/mainPersonal.js",
			title: "個人ナレッジ"
		},
	},
	filenameHashing: false,
	transpileDependencies: true,
}
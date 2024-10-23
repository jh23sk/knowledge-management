/**
 * 共通関数
 */

/* 引数のカテゴリリストから、IDが一致する名称を返す */
//export function getCategoryName(categoryList, categoryId) {
//		const category = categoryList.find(category => category.id === categoryId);
//		return category ? category.name : "";
//}

/* 引数のマスタ配列から、IDが一致する名称を返す */
export function getMasterName(masterDataLists, id) {
	if(id == "") {
		return "未選択";
	}
//	if(id == "new") {
//		return "新規追加";
//	}
	
	const masterDataList = masterDataLists.find(masterDataList => masterDataList.id === id);
	return masterDataList ? masterDataList.name : "";
}

/* 選択したカテゴリーIDから、サブカテゴリーを絞り込んで返却  */
export function createSubCategories(mstSubcategories, categoryId) {
	const showSubCategories = mstSubcategories.filter(mstSubcategory => {
		return mstSubcategory.categoryId == categoryId;
	});
	return showSubCategories;
}
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
	if(id == "" || id == null) {
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

/* 「yyyy-MM-dd HH:mm」形式にフォーマット */
export function formatDate(strDate) {
	const date = new Date(strDate);
	// Date型に変換できない場合はそのまま返却
	if (isNaN(date.getTime()) || strDate == "" || strDate == null) {
		return strDate;
	}
	
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0'); // 月は0から始まるので+1
	const day = String(date.getDate()).padStart(2, '0');
	const hours = String(date.getHours()).padStart(2, '0');
	const minutes = String(date.getMinutes()).padStart(2, '0');

	return `${year}-${month}-${day} ${hours}:${minutes}`;
  }
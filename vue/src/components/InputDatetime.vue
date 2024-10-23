<template>
	<VueDatePicker
		v-model="date"
		:format="'yyyy-MM-dd HH:mm'"
		model-type="timestamp"
		:enable-time-picker="true"
		week-start="0"
		locale="ja"
		placeholder="日付を選択"
		select-text="選択"
		cancel-text="ｷｬﾝｾﾙ"
		:show-now-button="true"
		now-button-label="現在を選択"
		:auto-position="false"
		style="max-width: 210px;"
	></VueDatePicker>
</template>

<script>
export default {
	name: 'cmp-input-datetime',
	props: {
		propDate: String,
	},
	data() {
		return {
			date: null,
		}
	},
	watch: {
		// 入力値が変更されたら親要素へバインド
		date(newValue) {
			this.$emit('update:propDate', newValue);
		}
	},
}
</script>

<!-- <style scoped> -->
<style>
</style>

<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">나만의 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<MyMenuListItem></MyMenuListItem>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MyMenuListItem from '@/components/menu/MyMenuListItem.vue';

export default {
	components: {
		BlackHeader,
		MyMenuListItem,
	},
};
</script>

<style></style>

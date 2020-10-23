<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">나만의 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuCountBox></MenuCountBox>
			{{ $route.params.menuInOrder.menu.menuName }}
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuCountBox from '@/components/menu/MenuCountBox.vue';

export default {
	components: {
		BlackHeader,
		MenuCountBox,
	},
	name: 'MyMenuItemDetail',
};
</script>

<style></style>

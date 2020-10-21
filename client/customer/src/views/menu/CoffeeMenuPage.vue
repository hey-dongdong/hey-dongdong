<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">커피류</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuListHeader></MenuListHeader>
			<ListItem></ListItem>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuListHeader from '@/components/menu/MenuListHeader.vue';
import ListItem from '@/components/menu/ListItem.vue';

export default {
	components: {
		BlackHeader,
		MenuListHeader,
		ListItem,
	},
};
</script>

<style></style>

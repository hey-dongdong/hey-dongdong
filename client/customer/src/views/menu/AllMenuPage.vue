<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">전체 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<ul>
				<li v-for="menuItem in menuItems" :key="menuItem.menuId">
					{{ menuItem.menuName }}
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import { fetchAllMenu } from '@/api/index';

export default {
	components: {
		BlackHeader,
	},
	data() {
		return {
			menuItems: [],
		};
	},
	methods: {
		async fetchData() {
			const { data } = await fetchAllMenu();
			console.log(data.payload.menus);
			//this.menuItems = data.menus;
		},
	},
	created() {
		this.fetchData();
	},
};
</script>

<style></style>

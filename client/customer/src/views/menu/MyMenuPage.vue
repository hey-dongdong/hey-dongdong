<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">나만의 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<p class="mymenu-text">
				주문했던 메뉴를 나만의 메뉴로 저장하고<br />더 간편하게 음료를 주문해보세요.
			</p>
			<MyMenuListItem v-for="item in myMenuItems" :key="item.myMenuId" :myMenuItem="item">
			</MyMenuListItem>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MyMenuListItem from '@/components/menu/MyMenuListItem.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		BlackHeader,
		MyMenuListItem,
	},
	computed: {
		...mapGetters(['myMenuItems']),
	},
	created() {
		this.$store.dispatch('FETCH_MY_MENUS');
	},
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/menu/detail');
		},
	},
};
</script>

<style></style>

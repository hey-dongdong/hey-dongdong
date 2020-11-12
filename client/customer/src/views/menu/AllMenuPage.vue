<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">전체 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuListHeader></MenuListHeader>
			<div class="menu-card-list">
				<MenuListItem
					v-for="item in menuItems"
					:key="item.menuId"
					:menuItem="item"
				></MenuListItem>
			</div>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuListHeader from '@/components/menu/MenuListHeader.vue';
import MenuListItem from '@/components/menu/MenuListItem.vue';
import { mapGetters } from 'vuex';
import { getUserFromCookie } from '@/utils/cookies';

export default {
	components: {
		BlackHeader,
		MenuListHeader,
		MenuListItem,
	},
	computed: {
		...mapGetters(['menuItems']),
	},
	created() {
		const data = {
			header: {
				name: 'GetAllMenusRequest',
				userId: getUserFromCookie(),
			},
			payload: {
				storeId: localStorage.getItem('store-id') || 1,
			},
		};
		this.$store.dispatch('FETCH_MENUS', data);
	},
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/menu/detail');
		},
	},
};
</script>

<style></style>

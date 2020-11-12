<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">에이드류</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuListHeader></MenuListHeader>
			<div class="menu-card-list">
				<MenuListItem
					v-for="item in menuItems"
					:key="item.menuId"
					:menuItem="item"
					v-if="item.categoryId === 3"
				></MenuListItem>
			</div>
		</div>
		<ToastPopup v-bind:show="isSuccess" @close="closeToast">
			<span slot="toast-message">장바구니에 메뉴를 추가했습니다.</span>
		</ToastPopup>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuListHeader from '@/components/menu/MenuListHeader.vue';
import MenuListItem from '@/components/menu/MenuListItem.vue';
import { mapGetters } from 'vuex';
import { getUserFromCookie } from '@/utils/cookies';
import ToastPopup from '@/components/common/ToastPopup.vue';

export default {
	components: {
		BlackHeader,
		MenuListHeader,
		MenuListItem,
		ToastPopup,
	},
	data() {
		return {
			isSuccess: this.$route.params.isSuccess,
		};
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
				storeId: localStorage.getItem('store-id'),
			},
		};
		this.$store.dispatch('FETCH_MENUS', data);
		if (this.isSuccess == true) {
			this.isSuccess = false;
			let timer;
			clearTimeout(timer);
			timer = setTimeout(() => (this.isSuccess = true), 1);
		}
	},
	methods: {
		closeToast() {
			this.isSuccess = false;
		},
	},
};
</script>

<style></style>

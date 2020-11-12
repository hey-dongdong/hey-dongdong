<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">히스토리</h1>
		</BlackHeader>
		<div class="blackbg">
			<div class="menu-header-history">
				<span>최신 순</span>
			</div>
			<HistoryListItem
				v-for="item in historyItems"
				:key="item.orderId"
				:historyItem="item"
			>
			</HistoryListItem>
		</div>
		<ToastPopup v-bind:show="isSuccess" @close="closeToast">
			<span slot="toast-message">장바구니에 메뉴를 추가했습니다.</span>
		</ToastPopup>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import HistoryListItem from '@/components/order/HistoryListItem.vue';
import { mapGetters } from 'vuex';
import store from '@/store/index';
import ToastPopup from '@/components/common/ToastPopup.vue';

export default {
	components: {
		BlackHeader,
		HistoryListItem,
		ToastPopup,
	},
	data() {
		return {
			isSuccess: this.$route.params.isSuccess,
		};
	},
	computed: {
		...mapGetters(['historyItems']),
	},
	created() {
		const data = {
			header: {
				name: 'GetUserHistoryRequest',
				userId: store.state.userId,
			},
			payload: {},
		};
		this.$store.dispatch('FETCH_HISTORY', data);
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

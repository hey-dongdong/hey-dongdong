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
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import HistoryListItem from '@/components/order/HistoryListItem.vue';
import { mapGetters } from 'vuex';
import store from '@/store/index';

export default {
	components: {
		BlackHeader,
		HistoryListItem,
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
	},
};
</script>

<style></style>

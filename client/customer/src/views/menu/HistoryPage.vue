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
				:key="item.orderInfo.orderId"
				:historyItem="item"
			>
			</HistoryListItem>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import HistoryListItem from '@/components/menu/HistoryListItem.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		BlackHeader,
		HistoryListItem,
	},
	computed: {
		...mapGetters(['historyItems']),
	},
	created() {
		this.$store.dispatch('FETCH_HISTORY');
	},
};
</script>

<style></style>

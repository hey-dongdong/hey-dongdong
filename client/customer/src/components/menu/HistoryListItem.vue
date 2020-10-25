<template>
	<div class="menu-card" @click="goToHistoryDetail(historyItem)">
		<img src="@/assets/cappuccino.png" alt="메뉴이미지" class="menu-img" />
		<div class="menu-text">
			<span class="menu-name">{{ historyItem.menuName }}</span>
			<span class="menu-detail history">
				<span class="menu-detail-text">
					{{ historyItem.orderInfo.orderAt }}
				</span>
				<span class="menu-detail-text">
					{{ historyItem.orderInfo.store.storeName }}
				</span>
				<span class="menu-detail-text"> {{ historyItem.orderInfo.totalPrice }}원 </span>
			</span>
		</div>
	</div>
</template>

<script>
export default {
	name: 'history-menu',
	props: {
		historyItem: Object,
	},
	methods: {
		goToHistoryDetail(item) {
			// console.log(item.menuInOrder.menu.menuName);
			this.$router.push({
				name: 'history-detail',
				path: '/history/detail',
				params: item,
			});
		},
	},
};
</script>

<style></style>

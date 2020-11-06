<template>
	<div class="menu-card" @click="goToHistoryDetail(historyItem)">
		<img
			:src="
				historyItem.menu.menuThumbnail
					? require('@/assets/menu' + historyItem.menu.menuThumbnail)
					: require('@/assets/drink.png')
			"
			alt="메뉴이미지"
			class="menu-img"
		/>
		<div class="menu-text">
			<span class="menu-name">
				{{ historyItem.menu.menuName }}
				<span v-if="historyItem.totalCount > 1">
					외 {{ historyItem.totalCount - 1 }} 개
				</span>
			</span>

			<span class="menu-detail history">
				<span class="menu-detail-text">
					{{ historyItem.orderAt }}
				</span>
				<span class="menu-detail-text">
					{{ historyItem.store.storeName }}
				</span>
				<span class="menu-detail-text"> {{ historyItem.totalPrice }}원 </span>
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

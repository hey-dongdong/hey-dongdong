<template>
	<div class="menu-card" @click="goToMenuDetail(menuItem)">
		<img
			:src="
				menuItem.imgUrl
					? require('@/assets' + menuItem.imgUrl)
					: require('@/assets/drink.png')
			"
			alt="메뉴이미지"
			class="menu-img"
		/>
		<div class="menu-text">
			<span class="menu-name">{{ menuItem.menuName }}</span>
			<span class="menu-detail">
				<span v-if="menuItem.smallHotPrice != null"
					>HOT {{ menuItem.smallHotPrice }}원</span
				>
				<span v-if="menuItem.smallHotPrice != null && menuItem.smallIcePrice != null"
					>,
				</span>
				<span v-if="menuItem.smallIcePrice != null"
					>ICE {{ menuItem.smallIcePrice }}원</span
				>
			</span>
		</div>
	</div>
</template>

<script>
export default {
	name: 'menu-item',
	props: {
		menuItem: Object,
	},
	methods: {
		goToMenuDetail(item) {
			// console.log(item.menuInOrder.menu.menuName);
			this.$router.push({
				name: 'menu-detail',
				path: '/menu/detail',
				params: item,
			});
		},
	},
};
</script>

<style></style>

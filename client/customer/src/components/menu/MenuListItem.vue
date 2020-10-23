<template>
	<div class="menu-card">
		<img
			:src="
				menuItem.imgUrl
					? require('@/assets' + menuItem.imgUrl)
					: require('@/assets/cappuccino.png')
			"
			alt="메뉴이미지"
			class="menu-img"
		/>
		<div class="menu-text">
			<span class="menu-name">{{ menuItem.menuName }}</span>
			<span class="menu-detail">
				<span v-if="menuItem.hotPrice != null">HOT {{ menuItem.hotPrice }}원</span>
				<spam v-if="menuItem.hotPrice != null && menuItem.icePrice != null">, </spam>
				<span v-if="menuItem.icePrice != null">ICE {{ menuItem.icePrice }}원</span>
			</span>
		</div>
	</div>
</template>

<script>
export default {
	name: 'menu',
	props: {
		menuItem: Object,
	},
};
</script>

<style></style>

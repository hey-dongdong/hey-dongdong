<template>
	<div class="menu-card">
		<img
			:src="
				myMenuItem.menuInOrder.menu.imgUrl
					? require('@/assets' + myMenuItem.menuInOrder.menu.imgUrl)
					: require('@/assets/cappuccino.png')
			"
			alt="메뉴이미지"
			class="menu-img"
		/>
		<div class="menu-text">
			<span class="menu-name">{{ myMenuItem.menuInOrder.menu.menuName }}</span>
			<span class="menu-detail">
				<span v-if="myMenuItem.menuInOrder.option.basicOption.isHot">HOT </span>
				<span v-if="!myMenuItem.menuInOrder.option.basicOption.isHot">ICE </span>
				<span>{{ myMenuItem.menuInOrder.price }}원</span>
			</span>
		</div>
	</div>
</template>

<script>
export default {
	name: 'my-menu',
	props: {
		myMenuItem: Object,
	},
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/my-menu/detail');
		},
	},
};
</script>

<style></style>

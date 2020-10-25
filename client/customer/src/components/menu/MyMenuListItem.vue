<template>
	<div class="menu-card">
		<div class="menu-card-without-x" @click="goToMyMenuDetail(myMenuItem)">
			<img
				:src="
					myMenuItem.menuInOrder.menu.imgUrl
						? require('@/assets' + myMenuItem.menuInOrder.menu.imgUrl)
						: require('@/assets/drink.png')
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

		<slot name="delete-button"></slot>
	</div>
</template>

<script>
export default {
	name: 'my-menu',
	props: {
		myMenuItem: Object,
	},
	methods: {
		goToMyMenuDetail(item) {
			// console.log(item.menuInOrder.menu.menuName);
			this.$router.push({
				name: 'my-menu-detail',
				path: '/my-menu/detail',
				params: item,
			});
		},
	},
};
</script>

<style></style>

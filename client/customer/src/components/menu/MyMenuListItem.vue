<template>
	<div>
		<ul>
			<li
				@click="goMyMenuDetailTmp"
				v-for="item in menuItems"
				:key="item.menuId"
				class="menu-card"
			>
				<img src="@/assets/cappuccino.png" alt="메뉴이미지" class="menu-img" />
				<div class="menu-text">
					<span class="menu-name">{{ item.menuName }}</span>
					<span class="menu-detail">
						HOT {{ item.hotPrice }}원, ICE {{ item.icePrice }}원
					</span>
				</div>
			</li>
		</ul>
	</div>
</template>

<script>
export default {
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/my-menu/detail');
		},
	},
};
</script>

<style></style>

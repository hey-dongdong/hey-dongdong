<template>
	<div>
		<ul>
			<li v-for="menuItem in menuItems" :key="menuItem.menuId" class="menu-card">
				<img src="@/assets/cappuccino.png" alt="메뉴이미지" class="menu-img" />
				<div class="menu-text">
					<span class="menu-name">{{ menuItem.menuName }}</span>
					<span class="menu-detail"
						>HOT {{ menuItem.hotPrice }}원, ICE {{ menuItem.icePrice }}원</span
					>
				</div>
			</li>
		</ul>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
	computed: {
		...mapGetters(['menuItems']),
	},
	created() {
		// this.$store.dispatch('FETCH_MENUS');
		// console.log(this.$route);
		const name = this.$route.name;
		if (name === 'menu-all') {
			this.$store.dispatch('FETCH_MENUS');
		}
	},
};
</script>

<style></style>

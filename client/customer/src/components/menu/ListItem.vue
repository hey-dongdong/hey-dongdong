<template>
	<div>
		<ul>
			<li
				@click="goMenuDetailTmp"
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
import { mapGetters } from 'vuex';

export default {
	computed: {
		...mapGetters(['menuItems']),
	},
	created() {
		// this.$store.dispatch('FETCH_MENUS');
		// console.log(this.$route);
		const name = this.$route.name;
		if (name === 'menu') {
			this.$store.dispatch('FETCH_MENUS');
		}
	},
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/menu/detail');
		},
	},
};
</script>

<style></style>

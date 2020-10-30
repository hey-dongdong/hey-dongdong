<template>
	<div>
		<ul>
			<li
				@click="goMenuDetailTmp"
				v-for="item in menuItems"
				:key="item.menuId"
				class="menu-card"
			>
				<img
					:src="
						item.imgUrl
							? require('@/assets' + item.imgUrl)
							: require('@/assets/cappuccino.png')
					"
					alt="메뉴이미지"
					class="menu-img"
				/>
				<div class="menu-text">
					<span class="menu-name">{{ item.menuName }}</span>
					<span class="menu-detail">
						<span v-if="item.hotPrice != null">HOT {{ item.hotPrice }}원</span>
						<spam v-if="item.hotPrice != null && item.icePrice != null">, </spam>
						<span v-if="item.icePrice != null">ICE {{ item.icePrice }}원</span>
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
		this.$store.dispatch('FETCH_MENUS');
	},
	methods: {
		goMenuDetailTmp() {
			this.$router.push('/menu/detail');
		},
	},
};
</script>

<style></style>

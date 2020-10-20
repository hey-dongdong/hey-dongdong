<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">전체 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<MenuListHeader></MenuListHeader>
			<ul>
				<li
					v-for="menuItem in menuItems"
					:key="menuItem.menuId"
					class="menu-card"
				>
					<img
						src="@/assets/cappuccino.png"
						alt="메뉴이미지"
						class="menu-img"
					/>
					<div class="menu-text">
						<span class="menu-name">{{ menuItem.menuName }}</span>
						<span class="menu-detail"
							>HOT {{ menuItem.hotPrice }}원, ICE
							{{ menuItem.icePrice }}원</span
						>
					</div>
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import MenuListHeader from '@/components/menu/MenuListHeader.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		BlackHeader,
		MenuListHeader,
	},
	computed: {
		...mapGetters(['menuItems']),
	},
	created() {
		this.$store.dispatch('FETCH_MENUS');
	},
	methods: {
		getImgUrl(url) {
			return require('' + url);
		},
	},
};
</script>

<style></style>

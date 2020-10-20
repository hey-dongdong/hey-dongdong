<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">전체 메뉴</h1>
		</BlackHeader>
		<div class="blackbg">
			<ul>
				<li v-for="menuItem in menuItems" :key="menuItem.menuId">
					<div>
						<img :src="menuItem.imgUrl" alt="메뉴이미지" />
						<span>{{ menuItem.menuName }}</span>
						<span
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
import { mapGetters } from 'vuex';

export default {
	components: {
		BlackHeader,
	},
	computed: {
		...mapGetters(['menuItems']),
	},
	created() {
		this.$store.dispatch('FETCH_MENUS');
	},
};
</script>

<style></style>

<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">히스토리</h1>
		</BlackHeader>
		<div class="blackbg">
			<div class="menu-header-history">
				<span>최신 순</span>
			</div>
			<ul>
				<li v-for="item in historyItems" :key="item.menuId" class="menu-card">
					<img src="@/assets/cappuccino.png" alt="메뉴이미지" class="menu-img" />
					<div class="menu-text">
						<span class="menu-name">{{ item.menuName }}</span>
						<span class="menu-detail history">
							<span class="menu-detail-text">
								{{ item.orderInfo.orderAt }}
							</span>
							<span class="menu-detail-text">
								{{ item.orderInfo.store.storeName }}
							</span>
							<span class="menu-detail-text"> {{ item.orderInfo.totalPrice }}원 </span>
						</span>
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
		...mapGetters(['historyItems']),
	},
	created() {
		this.$store.dispatch('FETCH_HISTORY');
	},
};
</script>

<style></style>

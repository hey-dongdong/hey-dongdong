<template>
	<div class="order-detail">
		<div class="order-detail-title">주문 음료</div>
		<ul>
			<li
				v-for="item in orderDetail.menus"
				:key="item.menuInOrderId"
				class="ordered-menu-card"
			>
				<div class="ordered-menu-card-header">
					<div class="ordered-menuname">{{ item.menuInfo.menu.menuName }}</div>
					<div class="star-checkbox">
						<input type="checkbox" value="star" id="star" />
						<label for="star"></label>
					</div>
				</div>

				<ul class="ordered-item-list">
					<li>컵 선택: 텀블러</li>
					<li>HOT / ICE : ICE</li>
					<li>SIZE : 대</li>
					<li>퍼스널 옵션: 샷 추가, 연유 추가</li>
				</ul>
				<div class="ordered-menu-card-footer">
					<span class="ordered-menu-price">2000원</span>
					<div class="ordered-menu-count">2잔</div>
				</div>
			</li>
		</ul>
	</div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
	computed: {
		...mapGetters(['orderDetail']),
	},
	created() {
		this.$store.dispatch('FETCH_ORDER_DETAIL');
	},
};
</script>

<style></style>

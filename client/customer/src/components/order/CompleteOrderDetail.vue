<template>
	<div class="order-detail">
		<div class="order-detail-title">주문 상세 내역</div>
		<ul class="order-detail-list">
			<li>주문번호: {{ completeOrderDetail.orderInfo.orderId }}</li>
			<li>주문고객: {{ user }} 님</li>
			<li>주문일시: {{ orderAt }}</li>
			<li>수령매장: {{ store }}</li>
			<li>총 금액: {{ completeOrderDetail.orderInfo.totalPrice }}원</li>
		</ul>
	</div>
</template>

<script>
import { getUserNameFromCookie } from '@/utils/cookies';

export default {
	name: 'complete-order-detail',
	props: {
		completeOrderDetail: Object,
	},
	data() {
		return {
			store: '',
			user: '',
			orderAt: '',
		};
	},
	created() {
		this.store = this.completeOrderDetail.orderInfo.store.storeName;
		this.user = getUserNameFromCookie();
		this.orderAt = this.completeOrderDetail.orderInfo.orderAt.toString().substring(0, 19);
	},
};
</script>

<style></style>

<template>
	<div class="order-detail">
		<div class="order-detail-title">주문 상세 내역</div>
		<ul class="order-detail-list">
			<li>주문번호: {{ orderDetail.orderInfo.orderId }}</li>
			<li>주문고객: {{ username }}</li>
			<li>주문일시: {{ orderDetail.orderInfo.orderAt }}</li>
			<li>수령매장: {{ orderDetail.orderInfo.store.storeName }}</li>
			<li>총 금액: {{ orderDetail.orderInfo.totalPrice }}원</li>
		</ul>
	</div>
</template>

<script>
import { getUserNameFromCookie } from '@/utils/cookies';

export default {
	name: 'order-detail',
	props: {
		orderDetail: Object,
	},
	data() {
		return {
			username: getUserNameFromCookie(),
		};
	},
	created() {},
};
</script>

<style></style>

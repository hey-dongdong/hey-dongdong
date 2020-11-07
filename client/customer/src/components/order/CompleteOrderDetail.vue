<template>
	<div class="order-detail">
		<div class="order-detail-title">주문 상세 내역</div>
		<ul class="order-detail-list">
			<li>주문번호: 301</li>
			<li>주문고객: {{ user }} 님</li>
			<li>주문일시: 2020.10.30 15:34</li>
			<li>수령매장: {{ store }}</li>
			<li>총 금액: 3500원</li>
		</ul>
	</div>
</template>

<script>
import { getUserFromCookie } from '@/utils/cookies';

export default {
	name: 'complete-order-detail',
	props: {
		completeOrderDetail: Object,
	},
	data() {
		return {
			store: '',
			user: '',
		};
	},
	created() {
		this.store = localStorage.getItem('store');
		this.user = getUserFromCookie();
	},
};
</script>

<style></style>

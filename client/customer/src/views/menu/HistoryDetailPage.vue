<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">히스토리</h1>
		</BlackHeader>
		<div class="blackbg">
			<OrderDetail :orderDetail="orderDetail"></OrderDetail>
			<div class="order-detail-title">주문 음료</div>
			<OrderItems
				v-for="item in orderDetail.menus"
				:key="item.menuInfo.menuInOrderId"
				:orderMenuItem="item"
				@toggle-like="toggleLike"
			></OrderItems>
			<div class="greenbtn-small-set">
				<button type="button" class="greenbtn-small">이대로 주문하기</button>
				<button type="button" class="greenbtn-small">장바구니에 담기</button>
			</div>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import OrderDetail from '@/components/menu/OrderDetail.vue';
import OrderItems from '@/components/menu/OrderItems.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		BlackHeader,
		OrderDetail,
		OrderItems,
	},
	computed: {
		...mapGetters(['orderDetail']),
	},
	created() {
		this.$store.dispatch('FETCH_ORDER_DETAIL');
	},
	methods: {
		toggleLike({ id, checked }) {
			console.log(id, checked);
		},
	},
};
</script>

<style></style>

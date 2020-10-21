<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">히스토리</h1>
		</BlackHeader>
		<div class="blackbg">
			<OrderDetail></OrderDetail>
			<OrderItems></OrderItems>
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

export default {
	components: {
		BlackHeader,
		OrderDetail,
		OrderItems,
	},
};
</script>

<style></style>

<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">주문 완료</h1>
		</BlackHeader>
		<div class="blackbg complete">
			<div class="barcode-container">
				<span class="order-complete-msg">주문이 완료되었습니다.</span>
				<div class="order-complete-store-msg">
					<span class="order-complete-store">공학관점</span>
					<span>에서 30분 이내에 음료를 수령해주세요.</span>
				</div>
				<img src="../../assets/barcode.png" alt="barcode" class="barcode complete" />
			</div>
			<OrderDetail></OrderDetail>
			<OrderItems></OrderItems>
			<div class="greenbtn-small-set">
				<button @click="goMain" type="submit" class="greenbtn fixed cart-menu">
					<span>메인으로 돌아가기</span>
				</button>
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
	methods: {
		goMain() {
			this.$router.push('/main');
		},
	},
};
</script>

<style></style>

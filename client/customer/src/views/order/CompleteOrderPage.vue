<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">주문 완료</h1>
		</BlackHeader>
		<div class="blackbg complete">
			<div class="barcode-container">
				<span class="order-complete-msg">주문이 완료되었습니다.</span>
				<div class="order-complete-store-msg">
					<span class="order-complete-store">{{ store }}</span>
					<span>에서 30분 이내에 음료를 수령해주세요.</span>
				</div>
				<!-- <img src="../../assets/barcode.png" alt="barcode" class="barcode complete" /> -->
				<VueBarcode
					v-bind:value="barcodeValue"
					class="barcode-box"
					height="50"
					format="CODE39"
				></VueBarcode>
			</div>
			<CompleteOrderDetail
				:completeOrderDetail="this.$route.params"
			></CompleteOrderDetail>
			<CompleteOrderItems
				v-for="item in this.$route.params.menus"
				:key="item.id"
				:completeOrderMenuItem="item"
				@toggle-like="toggleLike"
			></CompleteOrderItems>
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
import VueBarcode from 'vue-barcode';
import CompleteOrderDetail from '@/components/order/CompleteOrderDetail.vue';
import CompleteOrderItems from '@/components/order/CompleteOrderItems.vue';
import { getUserFromCookie } from '@/utils/cookies';
import { addMyMenu } from '@/api/menus';

export default {
	components: {
		BlackHeader,
		VueBarcode,
		CompleteOrderDetail,
		CompleteOrderItems,
	},
	data() {
		return {
			barcodeValue: 0,
			store: '',
		};
	},
	created() {
		this.barcodeValue = this.$route.params.orderId;
		this.store = localStorage.getItem('store');
	},
	methods: {
		async toggleLike({ id, checked }) {
			console.log(id, checked);
			if (checked == true) {
				const data = {
					header: {
						name: 'AddMyMenuRequest',
						userId: getUserFromCookie(),
					},
					payload: {
						menuInOrderId: id,
					},
				};
				await addMyMenu(data);
			}
		},
		goMain() {
			this.$router.push('/main');
		},
	},
};
</script>

<style></style>

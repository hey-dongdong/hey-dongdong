<template>
	<div class="card-container">
		<div class="card">
			<div class="menu-list">
				<MenuInCard
					v-for="menuItem in orderItem.menus"
					:key="menuItem.id"
					:menuItem="menuItem"
				></MenuInCard>
			</div>
			<span class="time">{{ orderItem.orderInfo.orderAt }}</span>
		</div>
		<ButtonBox>
			<button slot="button2" type="button" @click="completeMakingOrder">
				<ion-icon name="checkmark-circle" class="check"></ion-icon>
				<span>제조완료</span>
			</button>
		</ButtonBox>
	</div>
</template>

<script>
import MenuInCard from './MenuInCard.vue';
import ButtonBox from './ButtonBox.vue';
import { updateOrderProgress } from '@/api/index';

export default {
	name: 'order-item',
	props: {
		orderItem: Object,
	},
	components: {
		MenuInCard,
		ButtonBox,
	},
	methods: {
		async completeMakingOrder() {
			const data = {
				header: {
					name: 'UpdateOrderProgressRequest',
					userId: 'admin',
				},
				payload: {
					orderId: this.orderItem.orderInfo.orderId,
					progress: 'READY',
				},
			};
			await updateOrderProgress(data);
			this.$emit('fetch-again');
		},
	},
};
</script>

<style></style>

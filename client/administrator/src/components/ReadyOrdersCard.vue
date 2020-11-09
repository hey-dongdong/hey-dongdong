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
			<button slot="button1" type="button" class="accept ready" @click="doneOrder">
				<span>수령완료</span>
				<ion-icon name="checkmark-circle" class="check"></ion-icon>
			</button>
			<button slot="button2" type="button" @click="noShowOrder">
				<ion-icon name="close-circle" class="x"></ion-icon>
				<span>노쇼</span>
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
		async doneOrder() {
			const data = {
				header: {
					name: 'UpdateOrderProgressRequest',
					userId: 'admin',
				},
				payload: {
					orderId: this.orderItem.orderInfo.orderId,
					progress: 'DONE',
				},
			};
			await updateOrderProgress(data);
			this.$emit('fetch-again');
		},
		async noShowOrder() {
			const data = {
				header: {
					name: 'UpdateOrderProgressRequest',
					userId: 'admin',
				},
				payload: {
					orderId: this.orderItem.orderInfo.orderId,
					progress: 'NOSHOW',
				},
			};
			await updateOrderProgress(data);
			this.$emit('fetch-again');
		},
	},
};
</script>

<style></style>

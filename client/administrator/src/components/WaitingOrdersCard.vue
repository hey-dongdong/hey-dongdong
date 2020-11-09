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
			<button slot="button1" type="button" class="accept">
				<span>수락</span>
				<ion-icon name="checkmark-circle" class="check"></ion-icon>
			</button>
			<button slot="button2" type="button">
				<ion-icon name="close-circle" class="x"></ion-icon>
				<span>거절</span>
			</button>
		</ButtonBox>
	</div>
</template>

<script>
import MenuInCard from './MenuInCard.vue';
import ButtonBox from './ButtonBox.vue';

export default {
	name: 'order-item',
	props: {
		orderItem: Object,
	},
	components: {
		MenuInCard,
		ButtonBox,
	},
};
</script>

<style></style>

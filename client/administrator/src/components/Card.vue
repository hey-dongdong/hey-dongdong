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
			<span class="time">16:04, 9/7 월, 2020</span>
		</div>

		<div class="button-container">
			<button type="button" class="accept">
				<span>수락</span>
				<ion-icon name="checkmark-circle" class="check"></ion-icon>
			</button>
			<button type="button">
				<ion-icon name="close-circle" class="x"></ion-icon>
				<span>거절</span>
			</button>
		</div>
	</div>
</template>

<script>
import MenuInCard from './MenuInCard.vue';

export default {
	name: 'order-item',
	props: {
		orderItem: Object,
	},
	components: {
		MenuInCard,
	},
};
</script>

<style></style>

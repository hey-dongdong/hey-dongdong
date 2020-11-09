<template>
	<div class="card-container history">
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

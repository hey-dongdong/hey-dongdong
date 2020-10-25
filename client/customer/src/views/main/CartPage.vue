<template>
	<div>
		<header class="blackheader">
			<ion-icon name="arrow-back" class="header-left" @click="$router.go(-1)"></ion-icon>
			<h1 class="pagename">장바구니</h1>
		</header>
		<div class="blackbg">
			<div class="cart-container">
				<div class="cart-store-location">
					<span class="cart-store">공학관점</span>
					<span class="cart-exp">주문이 완료되면 픽업하러 오세요.</span>
				</div>
				<CartListItem
					v-for="item in cartItems"
					:key="item.id"
					:cartItem="item"
				></CartListItem>
				<ul class="cart-menu-list"></ul>
				<router-link to="/menu/all" class="goback-menu">+ 더 담으러 가기</router-link>
				<button @click="completeOrder" type="submit" class="greenbtn fixed cart-menu">
					<span>주문하기</span>
				</button>
			</div>
		</div>
	</div>
</template>

<script>
import CartListItem from '@/components/main/CartListItem.vue';

export default {
	components: {
		CartListItem,
	},
	data() {
		return {
			cartItems: [],
			finalCartItems: [],
		};
	},
	created() {
		if (localStorage.length > 0) {
			for (let i = 0; i < localStorage.length; i++) {
				if (localStorage.key(i) !== 'loglevel:webpack-dev-server') {
					this.cartItems.push(JSON.parse(localStorage.getItem(localStorage.key(i))));
				}
			}
		}
	},
	methods: {
		completeOrder() {
			if (localStorage.length > 0) {
				for (let i = 0; i < localStorage.length; i++) {
					if (localStorage.key(i) !== 'loglevel:webpack-dev-server') {
						this.finalCartItems.push(
							JSON.parse(localStorage.getItem(localStorage.key(i))),
						);
					}
				}
			}
			this.$router.push({
				name: 'complete',
				path: '/complete',
				params: this.finalCartItems,
			});
		},
	},
};
</script>

<style></style>

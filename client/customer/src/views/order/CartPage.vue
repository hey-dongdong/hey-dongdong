<template>
	<div>
		<header class="blackheader">
			<ion-icon name="arrow-back" class="header-left" @click="$router.go(-1)"></ion-icon>
			<h1 class="pagename">장바구니</h1>
		</header>
		<div class="blackbg">
			<div class="cart-container">
				<div class="cart-store-location">
					<span class="cart-store">{{ store }}</span>
					<span class="cart-exp">주문이 완료되면 픽업하러 오세요.</span>
				</div>
				<CartListItem
					v-for="item in cartItems"
					:key="item.id"
					:cartItem="item"
					@set-price="setPrice"
				></CartListItem>
				<ul class="cart-menu-list"></ul>
				<router-link to="/menu/all" class="goback-menu">+ 더 담으러 가기</router-link>
				<button @click="completeOrder" type="submit" class="greenbtn fixed cart-menu">
					<span>{{ totalPrice }}원 주문하기</span>
				</button>
			</div>
		</div>
	</div>
</template>

<script>
import CartListItem from '@/components/order/CartListItem.vue';
import { getUserFromCookie } from '@/utils/cookies';
import { addOrder } from '@/api/index';

export default {
	components: {
		CartListItem,
	},
	data() {
		return {
			store: '',
			cartItems: [],
			finalCartItems: [{}],
			totalPrice: 0,
		};
	},
	created() {
		this.store = localStorage.getItem('store');

		if (localStorage.length > 0) {
			for (let i = 0; i < localStorage.length; i++) {
				if (
					localStorage.key(i) !== 'loglevel:webpack-dev-server' &&
					localStorage.key(i) !== 'store-id' &&
					localStorage.key(i) !== 'store' &&
					localStorage.key(i) !== 'nearest-store-id' &&
					localStorage.key(i) !== 'nearest-store'
				) {
					this.cartItems.push(JSON.parse(localStorage.getItem(localStorage.key(i))));
				}
			}
		}
		for (let i = 0; i < this.cartItems.length; i++) {
			this.totalPrice += this.cartItems[i].price;
		}
	},
	methods: {
		async completeOrder() {
			if (localStorage.length > 0) {
				var menus = [];
				let totalCount = 0;
				for (let i = 0; i < localStorage.length; i++) {
					if (
						localStorage.key(i) !== 'loglevel:webpack-dev-server' &&
						localStorage.key(i) !== 'store-id' &&
						localStorage.key(i) !== 'store' &&
						localStorage.key(i) !== 'nearest-store-id' &&
						localStorage.key(i) !== 'nearest-store'
					) {
						var item = JSON.parse(localStorage.getItem(localStorage.key(i)));
						var menu = {
							menuId: item.menu.menuId,
							option: item.option,
							price: item.price,
							count: item.count,
						};
						totalCount += item.count;
						menus.push(menu);
					}
				}
				const data = {
					header: {
						name: 'AddNewOrderRequest',
						userId: getUserFromCookie(),
					},
					payload: {
						newOrderInfo: {
							orderAt: '2020-11-06 12:02:00',
							progress: 'WAITING',
							totalCount: totalCount,
							totalPrice: this.totalPrice,
							isNoShow: false,
							storeId: localStorage.getItem('store-id'),
							userId: getUserFromCookie(),
						},
						menus: menus,
					},
				};
				await addOrder(data);
			}
			// if (localStorage.length > 0) {
			// 	for (let i = 0; i < localStorage.length; i++) {
			// if (
			// 	localStorage.key(i) !== 'loglevel:webpack-dev-server' &&
			// 	localStorage.key(i) !== 'store-id' &&
			// 	localStorage.key(i) !== 'store' &&
			// 	localStorage.key(i) !== 'nearest-store-id' &&
			// 	localStorage.key(i) !== 'nearest-store'
			// ) {
			// 			this.finalCartItems.push(
			// 				JSON.parse(localStorage.getItem(localStorage.key(i))),
			// 			);
			// 			console.log({ ...this.finalCartItems });
			// 		}
			// 	}
			// }
			// this.$router.push({
			// 	name: 'complete',
			// 	path: '/complete',
			// 	params: this.finalCartItems,
			// });
		},
		setPrice({ before, price }) {
			// console.log(id, before, price);
			this.totalPrice -= before;
			this.totalPrice += price;
		},
	},
};
</script>

<style></style>

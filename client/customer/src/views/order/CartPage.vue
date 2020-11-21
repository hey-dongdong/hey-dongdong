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
			<ModalPopup @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title red"
					>중복 주문 불가</span
				>
				<span slot="modal-content" class="modal-content order">
					이미 제조 중인 음료가 있습니다.<br />
					음료를 수령한 뒤 다시 주문해주세요.
				</span>
				<div slot="footer" class="popup-buttons">
					<button @click="confirm" class="popup-button red" type="button">확인</button>
				</div>
			</ModalPopup>
		</div>
		
	</div>
</template>

<script>
import CartListItem from '@/components/order/CartListItem.vue';
import { getUserFromCookie, getOrderIdFromCookie } from '@/utils/cookies';
import { addOrder, getProgress } from '@/api/order';
import ModalPopup from '@/components/common/ModalPopup.vue';

export default {
	components: {
		CartListItem,
		ModalPopup,
	},
	data() {
		return {
			store: '',
			cartItems: [],
			totalPrice: 0,
			modal: false,
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
					localStorage.key(i) !== 'nearest-store' &&
						localStorage.key(i) !== 'device-token'
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
						localStorage.key(i) !== 'nearest-store' &&
						localStorage.key(i) !== 'device-token'
					) {
						var item = JSON.parse(localStorage.getItem(localStorage.key(i)));
						var menu = {
							menu: {
								menuId: item.menu.menuId,
								menuName: item.menu.menuName,
							},
							option: item.option,
							price: item.price,
							count: item.count,
						};
						totalCount += item.count;
						menus.push(menu);
					}
				}
				var flag = 0;
				if(getOrderIdFromCookie() === '') {
					flag = 1;
				}
				if(flag === 0) {
					const orderData = {
						header: {
							name: "GetOrderProgressRequest",
							userId: getUserFromCookie(),
						},
						payload: {
							orderId: getOrderIdFromCookie(),
						},
					};
					try {
						const { data } = await getProgress(orderData);
						if(data.payload.progress === 'WAITING' ||
								data.payload.progress === 'MAKING' ||
								data.payload.progress === 'READY') {
							this.openModal();
						}
						else {
							flag = 1;
						}
					} catch (error) {
						flag = 1;
					}
				}
				if (flag === 1){
					let now = new Date();
					let year = now.getFullYear();
					let month = now.getMonth() + 1;
					let date = now.getDate();
					let hours = now.getHours();
					let minutes = now.getMinutes();
					let seconds = now.getSeconds();
					var time = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
					const data2 = {
						header: {
							name: 'AddNewOrderRequest',
							userId: getUserFromCookie(),
						},
						payload: {
							orderInfo: {
								orderAt: time,
								progress: 'WAITING',
								totalCount: totalCount,
								totalPrice: this.totalPrice,
								isNoShow: false,
								store: {
									storeId: localStorage.getItem('store-id'),
								},
								user: {
									userId: getUserFromCookie(),
								},
							},
							menus: menus,
						},
					};
					const response = await addOrder(data2);
					console.log(response);
					for (let i = 0; i < localStorage.length; i++) {
						if (
							localStorage.key(i) !== 'loglevel:webpack-dev-server' &&
							localStorage.key(i) !== 'store-id' &&
							localStorage.key(i) !== 'store' &&
							localStorage.key(i) !== 'nearest-store-id' &&
							localStorage.key(i) !== 'nearest-store' &&
						localStorage.key(i) !== 'device-token'
						) {
							localStorage.removeItem(localStorage.key(i));
						}
					}
					this.$router.push({
						name: 'complete',
						path: '/complete',
						params: response.data.payload,
					});
				}
				
      }
		},
		setPrice({ before, price }) {
			this.totalPrice -= before;
			this.totalPrice += price;
		},
		openModal() {
			this.modal = true;
		},
		closeModal() {
			this.modal = false;
		},
		confirm() {
			this.closeModal();
		},
	},
};
</script>

<style></style>

<template>
	<div>
		<BlackHeader>
			<h1 slot="pagename">히스토리</h1>
		</BlackHeader>
		<div class="blackbg">
			<OrderDetail :orderDetail="historyDetail"></OrderDetail>
			<div class="order-detail-title">주문 음료</div>
			<OrderItems
				v-for="item in historyDetail.menus"
				:key="item.menuInOrderId"
				:orderMenuItem="item"
				@toggle-like="toggleLike"
			></OrderItems>
			<div class="greenbtn-small-set">
				<button type="button" class="greenbtn-small" @click="openModal()">
					이대로 주문하기
				</button>
				<button type="button" class="greenbtn-small" @click="addToCart">
					장바구니에 담기
				</button>
			</div>
			<ModalWithTwoBtn @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title mymenu">이대로 주문하기</span>
				<span slot="modal-content" class="modal-content">
					{{ $route.params.store.storeName }}에 <br />
					음료 {{ totalCount }}잔을 주문하시겠습니까?
				</span>
				<div slot="footer" class="popup-buttons">
					<button @click="doSend" class="popup-button" type="button">취소</button>
					<button @click="orderHistoryMenu" class="popup-button" type="button">
						주문하기
					</button>
				</div>
			</ModalWithTwoBtn>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import OrderDetail from '@/components/order/OrderDetail.vue';
import OrderItems from '@/components/order/OrderItems.vue';
import { mapGetters } from 'vuex';
import store from '@/store/index';
import { getUserFromCookie } from '@/utils/cookies';
import { addMyMenu, removeMyMenu } from '@/api/menus';
import ModalWithTwoBtn from '@/components/common/ModalWithTwoBtn.vue';
import { addOrder } from '@/api/order';

export default {
	components: {
		BlackHeader,
		OrderDetail,
		OrderItems,
		ModalWithTwoBtn,
	},
	data() {
		return {
			modal: false,
			totalCount: this.$route.params.totalCount,
			isSuccess: false,
		};
	},
	computed: {
		...mapGetters(['historyDetail']),
	},
	created() {
		const data = {
			header: {
				name: 'GetUserHistoryDetailRequest',
				userId: store.state.userId,
			},
			payload: {
				orderId: this.$route.params.orderId,
			},
		};
		this.$store.dispatch('FETCH_HISTORY_DETAIL', data);
	},
	methods: {
		openModal() {
			this.modal = true;
		},
		closeModal() {
			this.modal = false;
		},
		doSend() {
			this.closeModal();
		},
		async toggleLike({ id, checked, myMenuId }) {
			console.log(id, checked, myMenuId);
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
				this.$store.dispatch('FETCH_HISTORY_DETAIL', {
					header: {
						name: 'GetUserHistoryDetailRequest',
						userId: store.state.userId,
					},
					payload: {
						orderId: this.$route.params.orderId,
					},
				});
			} else {
				const data = {
					header: {
						name: 'RemoveMyMenuRequest',
						userId: getUserFromCookie(),
					},
					payload: {
						myMenuId: myMenuId,
					},
				};
				await removeMyMenu(data);
			}
		},
		async orderHistoryMenu() {
			let now = new Date();
			let year = now.getFullYear();
			let month = now.getMonth();
			let date = now.getDate();
			let hours = now.getHours();
			let minutes = now.getMinutes();
			let seconds = now.getSeconds();
			var time = `${year}-${month}-${date} ${hours}:${minutes}:${seconds}`;
			var menus = [];

			for (let i = 0; i < this.historyDetail.menus.length; i++) {
				var item = this.historyDetail.menus[i];
				var menu = {
					menu: {
						menuId: item.menu.menuId,
						menuName: item.menu.menuName,
					},
					option: item.option,
					price: item.price,
					count: item.count,
				};
				menus.push(menu);
			}

			const data = {
				header: {
					name: 'AddNewOrderRequest',
					userId: getUserFromCookie(),
				},
				payload: {
					orderInfo: {
						orderAt: time,
						progress: 'WAITING',
						totalCount: this.totalCount,
						totalPrice: this.$route.params.totalPrice,
						isNoShow: false,
						store: {
							storeId: this.$route.params.store.storeId,
						},
						user: {
							userId: getUserFromCookie(),
						},
					},
					menus: menus,
				},
			};
			const response = await addOrder(data);
			this.$router.push({
				name: 'complete',
				path: '/complete',
				params: response.data.payload,
			});
		},
		addToCart() {
			for (let i = 0; i < this.historyDetail.menus.length; i++) {
				var item = this.historyDetail.menus[i];
				let index = 0;
				var isSame = false;
				var menu = {
					menuId: item.menu.menuId,
					menuName: item.menu.menuName,
				};
				var option = item.option;
				var price = 0;
				var count = 0;
				if (localStorage.length > 0) {
					for (let i = 0; i < localStorage.length; i++) {
						if (Number(index) < Number(localStorage.key(i))) {
							index = localStorage.key(i);
							var cartItem = JSON.parse(localStorage.getItem(localStorage.key(i)));
							if (
								JSON.stringify(cartItem.menu) == JSON.stringify(menu) &&
								JSON.stringify(cartItem.option) == JSON.stringify(option)
							) {
								isSame = true;
								price = cartItem.price;
								count = cartItem.count;
								break;
							}
						}
					}
				}
				if (isSame == true) {
					var value = {
						id: Number(index),
						menu: menu,
						option: option,
						price: item.price + price,
						count: item.count + count,
					};
				} else {
					index = Number(index) + 1;
					value = {
						id: index,
						menu: menu,
						option: option,
						price: item.price,
						count: item.count,
					};
				}
				localStorage.setItem(index, JSON.stringify(value));
			}
			this.isSuccess = true;
			this.$router.push({
				name: 'history',
				path: '/history',
				params: {
					isSuccess: this.isSuccess,
				},
			});
		},
	},
};
</script>

<style></style>

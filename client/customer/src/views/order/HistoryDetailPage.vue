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
			<ModalPopup @close="closeModal" v-if="modal">
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
			</ModalPopup>
			<ToastPopup v-bind:show="isMyMenuAddSuccess" @close="closeAddToast">
				<span slot="toast-message">나만의 메뉴에 음료를 저장했습니다.</span>
			</ToastPopup>
			<ToastPopup v-bind:show="isMyMenuDeleteSuccess" @close="closeDeleteToast">
				<span slot="toast-message">나만의 메뉴에서 음료를 삭제했습니다.</span>
			</ToastPopup>
			<ModalPopup @close="closeOrderModal" v-if="orderModal">
				<span slot="modal-title" class="modal-title red"
					>중복 주문 불가</span
				>
				<span slot="modal-content" class="modal-content order">
					이미 제조 중인 음료가 있습니다.<br />
					음료를 수령한 뒤 다시 주문해주세요.
				</span>
				<div slot="footer" class="popup-buttons">
					<button @click="doOrderModalSend" class="popup-button red" type="button">확인</button>
				</div>
			</ModalPopup>
		</div>
	</div>
</template>

<script>
import BlackHeader from '@/components/common/BlackHeader.vue';
import OrderDetail from '@/components/order/OrderDetail.vue';
import OrderItems from '@/components/order/OrderItems.vue';
import { mapGetters } from 'vuex';
import store from '@/store/index';
import { getUserFromCookie, getOrderIdFromCookie } from '@/utils/cookies';
import { addMyMenu, removeMyMenu } from '@/api/menus';
import ModalPopup from '@/components/common/ModalPopup.vue';
import { addOrder, getProgress } from '@/api/order';
import ToastPopup from '@/components/common/ToastPopup.vue';

export default {
	components: {
		BlackHeader,
		OrderDetail,
		OrderItems,
		ModalPopup,
		ToastPopup,
	},
	data() {
		return {
			modal: false,
			orderModal: false,
			totalCount: this.$route.params.totalCount,
			isSuccess: false,
			isMyMenuAddSuccess: false,
			isMyMenuDeleteSuccess: false,
			connection: null,
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
		openOrderModal() {
			this.orderModal = true;
		},
		closeOrderModal() {
			this.orderModal = false;
		},
		doOrderModalSend() {
			this.closeOrderModal();
		},
		async toggleLike({ id, checked, myMenuId }) {
			if (checked == true) {
				this.isMyMenuDeleteSuccess = false;
				this.isMyMenuAddSuccess = true;
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
				this.isMyMenuAddSuccess = false;
				this.isMyMenuDeleteSuccess = true;
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
					const { data } = await getProgress(orderData);
					if(data.payload.progress === 'WAITING' ||
							data.payload.progress === 'MAKING' ||
							data.payload.progress === 'READY') {
						this.closeModal();
						this.openOrderModal();
					}
					else {
						flag = 1;
					}
				}
				if (flag === 1) {
					let now = new Date();
					let year = now.getFullYear();
					let month = now.getMonth() + 1;
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

					const data2 = {
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
					const response = await addOrder(data2);
					if(localStorage.getItem('store-id') !== this.$route.params.store.storeId) {
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
									localStorage.removeItem(localStorage.key(i));
								}
							}
						}
						localStorage.setItem('store-id', this.$route.params.store.storeId);
						localStorage.setItem('store', this.$route.params.store.storeName);
					}
					this.$router.push({
						name: 'complete',
						path: '/complete',
						params: response.data.payload,
						query: {
							storeName: this.$route.params.store.storeName,
						}
					});
			}
			
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
		closeAddToast() {
			this.isMyMenuAddSuccess = false;
		},
		closeDeleteToast() {
			this.isMyMenuDeleteSuccess = false;
		},
	},
};
</script>

<style></style>

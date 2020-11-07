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
				<button type="button" class="greenbtn-small">이대로 주문하기</button>
				<button type="button" class="greenbtn-small" @click="addToCart">
					장바구니에 담기
				</button>
			</div>
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
import { addMyMenu } from '@/api/menus';

export default {
	components: {
		BlackHeader,
		OrderDetail,
		OrderItems,
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
		async toggleLike({ id, checked }) {
			console.log(id, checked);
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
			}
		},
		addToCart() {
			let maxIndex = 0;
			if (localStorage.length > 0) {
				for (let i = 0; i < localStorage.length; i++) {
					if (Number(maxIndex) < Number(localStorage.key(i))) {
						maxIndex = localStorage.key(i);
					}
				}
			}
			maxIndex = Number(maxIndex) + 1;
			for (let i = 0; i < this.historyDetail.menus.length; i++) {
				var item = this.historyDetail.menus[i];
				console.log(item);
				var value = {
					id: maxIndex,
					menu: {
						menuId: item.menu.menuId,
						menuName: item.menu.menuName,
					},
					option: item.option,
					price: item.price,
					count: item.count,
				};
				localStorage.setItem(maxIndex, JSON.stringify(value));
				maxIndex++;
			}
		},
	},
};
</script>

<style></style>

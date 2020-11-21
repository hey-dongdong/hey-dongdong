<template>
	<div>
		<header class="header">
			<div class="header-middle">
				<div class="logo-title">
					<img src="../assets/logo.png" alt="로고" class="logo" />
					<h1 class="title">헤이동동</h1>
				</div>
				<div class="select-box">
					<select name="store" v-model="selected" v-on:change="selectStore">
						<option value="1">학생문화관점</option>
						<option value="2">도서관점</option>
						<option value="3">국제기숙사점</option>
						<option value="4">교육관점</option>
						<option value="5">경영관점</option>
						<option value="6">조형관점</option>
						<option value="7">음악관점</option>
						<option value="8">공학관점</option>
						<option value="9">종합과학관점</option>
						<option value="10">산학협력관점</option>
						<option value="11">동창회관점</option>
						<option value="12">학관점</option>
						<option value="13">체육관점</option>
						<option value="14">법학관점</option>
						<option value="15">헬렌관점</option>
					</select>
				</div>
			</div>
			<button type="button" class="link-to-history" @click="goToHistory">
				완료된 주문 >
			</button>
		</header>
		<div class="bg">
			<div class="sub-title">
				<span>들어온 주문</span>
			</div>
			<div class="card-list">
				<WaitingOrdersCard
					v-for="cardItem in orderItems ? orderItems.waitingOrders : []"
					:key="cardItem.id"
					:orderItem="cardItem"
					@fetch-again="fetchAgain"
				></WaitingOrdersCard>
			</div>
			<div class="sub-title">
				<span>제조 중인 주문</span>
			</div>
			<div class="card-list">
				<MakingOrdersCard
					v-for="cardItem in orderItems ? orderItems.makingOrders : []"
					:key="cardItem.id"
					:orderItem="cardItem"
					@fetch-again="fetchAgain"
				></MakingOrdersCard>
			</div>
			<div class="sub-title">
				<span>수령 대기 중인 주문</span>
			</div>
			<div class="card-list">
				<ReadyOrdersCard
					v-for="cardItem in orderItems ? orderItems.readyOrders : []"
					:key="cardItem.id"
					:orderItem="cardItem"
					@fetch-again="fetchAgain"
					@no-show="noShow"
				></ReadyOrdersCard>
			</div>
			<ModalPopup @close="closeModal" v-if="modal">
				<span slot="modal-title" class="modal-title red"
					>고객이 주문 후 방문하지 않았나요?</span
				>
				<span slot="modal-content" class="modal-content">
					아래버튼을 누르면 위 고객은 <span class="yellow">일정 기간 동안</span><br />
					<span class="yellow">헤이동동 서비스를 이용할 수 없게 됩니다.</span><br/>
					<span class="name">{{ this.userName }}</span><br />
					<span class="phone">{{ this.phone }}</span>
				</span>
				
				<div slot="footer" class="popup-buttons">
					<button @click="confirm" class="popup-button green" type="button">취소</button>
					<button @click="confirmNoShow" class="popup-button red" type="button">노쇼</button>
				</div>
			</ModalPopup>
		</div>
	</div>
</template>

<script>
import WaitingOrdersCard from '@/components/WaitingOrdersCard.vue';
import MakingOrdersCard from '@/components/MakingOrdersCard.vue';
import ReadyOrdersCard from '@/components/ReadyOrdersCard.vue';
import { mapGetters } from 'vuex';
import ModalPopup from '@/components/ModalPopup.vue';
import { updateOrderProgress } from '@/api/index';

export default {
	components: {
		WaitingOrdersCard,
		MakingOrdersCard,
		ReadyOrdersCard,
		ModalPopup,
	},
	data() {
		return {
			selected: this.$route.params.selectedStoreId || 1,
			modal: false,
			id: '',
			userName: '',
			phone: '',
		};
	},
	computed: {
		...mapGetters(['orderItems']),
	},
	created() {
		const data = {
			header: {
				name: 'GetStoreOrdersRequest',
				userId: 'admin',
			},
			payload: {},
		};
		setInterval(() => {
			this.$store.dispatch('FETCH_ORDERS', {
				id: this.selected,
				data: data,
			});
		}, 2000);
		
	},
	methods: {
		async selectStore(e) {
			const data = {
				header: {
					name: 'GetStoreOrdersRequest',
					userId: 'admin',
				},
				payload: {},
			};
			this.$store.dispatch('FETCH_ORDERS', {
				id: e.target.value,
				data: data,
			});
		},
		async fetchAgain() {
			const data = {
				header: {
					name: 'GetStoreOrdersRequest',
					userId: 'admin',
				},
				payload: {},
			};
			this.$store.dispatch('FETCH_ORDERS', {
				id: this.selected,
				data: data,
			});
		},
		goToHistory() {
			this.$router.push({
				name: 'history',
				path: '/history',
				params: {
					selectedStoreId: this.selected,
				},
			});
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
		noShow({ id, userName, phone }) {
			this.id = id;
			this.userName = userName;
			this.phone = phone;
			this.openModal();
		},
		async confirmNoShow() {
			const data = {
				header: {
					name: 'UpdateOrderProgressRequest',
					userId: 'admin',
				},
				payload: {
					orderId: this.id,
					progress: 'NOSHOW',
				},
			};
			await updateOrderProgress(data);
			this.fetchAgain();
			this.closeModal();
		}
	},
};
</script>

<style></style>

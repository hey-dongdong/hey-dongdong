<template>
	<div>
		<header class="header">
			<ion-icon name="arrow-back" class="arrow-back" @click="goBackToMain"></ion-icon>
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
		</header>
		<div class="bg history">
			<div class="sub-title history">
				<span>수령 완료된 주문</span>
				<div class="page-buttons">
					<button class="goto" @click="goToHistory">수령 완료</button> |
					<button class="goto" @click="goToNoShow">No Show</button>
				</div>
			</div>
		</div>
		<div class="bg history-card">
			<div class="history-card-list">
				<HistoryCard
					v-for="cardItem in historyItems ? historyItems.doneOrders : []"
					:key="cardItem.id"
					:orderItem="cardItem"
					@fetch-again="fetchAgain"
				></HistoryCard>
			</div>
		</div>
	</div>
</template>

<script>
import HistoryCard from '@/components/HistoryCard.vue';
import { mapGetters } from 'vuex';

export default {
	components: {
		HistoryCard,
	},
	data() {
		return {
			selected: this.$route.params.selectedStoreId || 1,
		};
	},
	computed: {
		...mapGetters(['historyItems']),
	},
	created() {
		const data = {
			header: {
				name: 'GetStoreHistoryRequest',
				userId: 'admin',
			},
			payload: {},
		};
		this.$store.dispatch('FETCH_HISTORY_ORDERS', {
			id: this.selected,
			data: data,
		});
	},
	methods: {
		async selectStore(e) {
			const data = {
				header: {
					name: 'GetStoreHistoryRequest',
					userId: 'admin',
				},
				payload: {},
			};
			this.$store.dispatch('FETCH_HISTORY_ORDERS', {
				id: e.target.value,
				data: data,
			});
		},
		goBackToMain() {
			this.$router.push({
				name: 'main',
				path: '/main',
				params: {
					selectedStoreId: this.selected,
				},
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
			this.$store.dispatch('FETCH_HISTORY_ORDERS', {
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
		goToNoShow() {
			this.$router.push({
				name: 'no-show',
				path: '/no-show',
				params: {
					selectedStoreId: this.selected,
				},
			});
		},
	},
};
</script>

<style></style>

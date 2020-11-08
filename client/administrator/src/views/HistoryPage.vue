<template>
	<div>
		<header class="header">
			<ion-icon name="arrow-back" class="arrow-back" @click="$router.go(-1)"></ion-icon>
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
		<div class="bg">
			<div class="sub-title">제조 완료된 주문</div>
			<div class="card-list">
				<Card></Card>
				<Card></Card>
				<Card></Card>
			</div>
		</div>
	</div>
</template>

<script>
import Card from '@/components/Card.vue';
import { fetchHistoryOrders } from '@/api/index';

export default {
	components: {
		Card,
	},
	data() {
		return {
			selected: 1,
		};
	},
	created() {
		console.log(this.selected);
	},
	methods: {
		async selectStore(e) {
			console.log(e.target.value);
			const data = {
				header: {
					name: 'GetStoreHistoryRequest',
					userId: 'admin',
				},
				payload: {},
			};
			const response = await fetchHistoryOrders(e.target.value, data);
			console.log(response.data);
		},
	},
};
</script>

<style></style>

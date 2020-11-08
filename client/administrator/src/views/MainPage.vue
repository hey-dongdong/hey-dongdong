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
			<router-link to="/history" class="link-to-history">완료된 주문 ></router-link>
		</header>
		<div class="bg">
			<div class="sub-title">
				들어온 주문
			</div>
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
import { mapGetters } from 'vuex';

export default {
	components: {
		Card,
	},
	data() {
		return {
			selected: 1,
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
		this.$store.dispatch('FETCH_ORDERS', {
			id: 1,
			data: data,
		});
	},
	methods: {
		async selectStore(e) {
			console.log(e.target.value);
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
	},
};
</script>

<style></style>

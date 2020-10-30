<template>
	<div>
		<header class="header">
			<div class="logo-title">
				<img src="../assets/logo.png" alt="로고" class="logo" />
				<h1 class="title">헤이동동</h1>
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

export default {
	components: {
		Card,
	},
};
</script>

<style></style>

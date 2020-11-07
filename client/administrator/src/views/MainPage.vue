<template>
	<div>
		<header class="header">
			<div class="header-middle">
				<div class="logo-title">
					<img src="../assets/logo.png" alt="로고" class="logo" />
					<h1 class="title">헤이동동</h1>
				</div>
				<v-container fluid>
					<v-row>
						<v-col cols="12">
							<v-combobox v-model="select" :items="items" color="#FFFFFF"></v-combobox>
						</v-col>
					</v-row>
				</v-container>
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
	data() {
		return {
			select: ['Vuetify', 'Programming'],
			items: ['Programming', 'Design', 'Vue', 'Vuetify'],
		};
	},
};
</script>

<style></style>

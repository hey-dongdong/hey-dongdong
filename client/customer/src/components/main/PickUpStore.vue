<template>
	<div class="pickup-store">
		<router-link to="/store-info" class="go-map">
			<img src="../../assets/location.png" alt="store map button" class="location" />
			<h2 class="pickup-location">픽업위치:</h2>
			<h2 class="storename">{{ store }}</h2>
		</router-link>
	</div>
</template>

<script>
export default {
	// props: ['location'],
	data() {
		return {
			store: '',
		};
	},
	created() {
		this.store = localStorage.getItem('store');
	},
};
</script>

<style></style>
